package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.authentication._

import scala.concurrent.Future

class AppMarketCommunicationRoutesModuleTest extends UnitTestSpec with ScalatestRouteTest with Directives {

  private val expectedClientKey = "clientKey"
  private val parser = new OauthSignatureParser()
  private val expectedClientSecret = "cclientecret"
  private val authorizationTokenGenerator = new AuthorizationTokenGenerator()

  val connectorCredentialsSupplier: CredentialsSupplier = (clientKey: String) =>
    if (clientKey == expectedClientKey)
      AppMarketCredentials(
        clientKey = expectedClientKey,
        clientSecret = expectedClientSecret
      )
    else throw new NoSuchElementException(s"The client key $clientKey is not known by the connector")

  it should "authenticate the caller successfully" in {
    //Given
    val testRequestUrl = "http://example.com"
    val testMarketplaceCredentials = AppMarketCredentials(
      clientKey = expectedClientKey,
      clientSecret = expectedClientSecret
    )
    val header = authorizationTokenGenerator.generateAuthorizationHeader(
      httpMethodName = "GET",
      resourceUrl = testRequestUrl,
      marketplaceCredentials = testMarketplaceCredentials
    )

    //When
    Get(testRequestUrl) ~> addHeader("Authorization", header) ~> testedRoute ~> check {
      //Then
      status shouldEqual StatusCodes.OK
      responseAs[String] shouldEqual expectedClientKey
    }
  }

  it should "fail authenticating an unknown clientId" in {
    //Given
    val testRequestUrl = "http://example.com"
    val unknownClientKey = expectedClientKey + "fsdfsd"
    val testClientCredentials = AppMarketCredentials(
      clientKey = unknownClientKey,
      clientSecret = expectedClientSecret
    )
    val authorizationTokenValue = authorizationTokenGenerator.generateAuthorizationHeader(
      httpMethodName = "GET",
      resourceUrl = testRequestUrl,
      marketplaceCredentials = testClientCredentials
    )

    //When
    Get(testRequestUrl) ~> addHeader("Authorization", authorizationTokenValue) ~> testedRoute ~> check {
      //Then
      status should not equal StatusCodes.OK
    }
  }

//  it should "fail authenticating a known clientId signed with the wrong secret" in {
//    //Given
//    val testRequestUrl = "http://example.com"
//    val wrongClientSecret = expectedClientSecret + "fsfs"
//    val testClientCredentials = AppMarketCredentials(
//      clientKey = expectedClientKey,
//      clientSecret = wrongClientSecret
//    )
//    val authorizationTokenValue = authorizationTokenGenerator.generateAuthorizationHeader(
//      httpMethodName = "GET",
//      resourceUrl = testRequestUrl,
//      marketplaceCredentials = testClientCredentials
//    )
//
//    //When
//    Get(testRequestUrl) ~> addHeader("Authorization", authorizationTokenValue) ~> testedRoute ~> check {
//      //Then
//      status should not equal StatusCodes.OK
//    }
//  }
  
  def authenticator(httpMethodName: String, uri: String)
                   (credentials: Option[HttpCredentials]): Future[AuthenticationResult[String]] =
    Future {
      credentials match {
        case Some(callerCredentials) =>

          val callerClientKey = callerCredentials.params("oauth_consumer_key")
          val connectorCredentials = connectorCredentialsSupplier.readCredentialsFor(callerClientKey)
          val expectedOAuthToken = authorizationTokenGenerator.generateAuthorizationHeader(
            httpMethodName = httpMethodName,
            resourceUrl = uri,
            timeStamp = callerCredentials.params("oauth_timestamp"),
            nonce = callerCredentials.params("oauth_nonce"),
            marketplaceCredentials = connectorCredentials
          )
          val expectedOAuthTokenParameters = parser.parse(expectedOAuthToken)

          if (expectedOAuthTokenParameters.oauthSignature == callerCredentials.params("oauth_signature")) {
            Right(callerClientKey)
          } else {
            Left(HttpChallenge(scheme = "OAuth", realm = None))
          }
        case _ =>
          Left(HttpChallenge(scheme = "OAuth", realm = None))
      }
    }

  val authenticate = (extractMethod & extractUri) tflatMap { case (httpMethod, url) =>
    authenticateOrRejectWithChallenge(authenticator(httpMethodName = httpMethod.value, uri = url.toString()) _)
  }
  
  val testedRoute = authenticate { clientId =>
    complete(clientId)
  }
}
