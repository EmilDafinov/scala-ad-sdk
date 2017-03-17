package com.github.emildafinov.ad.sdk.server

import java.util.Optional

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.authentication._
import com.github.emildafinov.ad.sdk.server.routing.directives.ConnectorAuthenticationDirective

class AppMarketCommunicationRoutesModuleTest extends UnitTestSpec with ScalatestRouteTest with Directives {

  private val expectedClientKey = "clientKey"
  private val parser = new OauthSignatureParser()
  private val expectedClientSecret = "cclientecret"
  private val authorizationTokenGenerator = new AuthorizationTokenGenerator()

  private val connectorCredentialsSupplier: AppMarketCredentialsSupplier = (clientKey: String) =>
    if (clientKey == expectedClientKey)
      Optional.of(
        AppMarketCredentialsImpl(
          clientKey = expectedClientKey,
          clientSecret = expectedClientSecret
        )
      )
    else throw new NoSuchElementException(s"The client key $clientKey is not known by the connector")

  private val authFactory = new OAuthAuthenticatorFactory(
    connectorCredentialsSupplier, 
    authorizationTokenGenerator,
    parser
  )
  
  private val testedDirective = ConnectorAuthenticationDirective(authFactory, connectorCredentialsSupplier)
  
  private val testedRoute =  testedDirective{ clientId =>
    complete(clientId.clientKey())
  }
  
  it should "authenticate the caller successfully" in {
    //Given
    val testRequestUrl = "http://example.com"
    val testMarketplaceCredentials = AppMarketCredentialsImpl(
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
    val testClientCredentials = AppMarketCredentialsImpl(
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
  
}
