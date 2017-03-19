package com.github.emildafinov.ad.sdk.authentication

import java.util.Optional

import akka.http.scaladsl.model.headers.{GenericHttpCredentials, HttpChallenge}
import akka.http.scaladsl.server.Directives.AuthenticationResult
import com.github.emildafinov.ad.sdk.UnitTestSpec
import org.apache.http.client.methods.HttpGet
import org.mockito.Mockito.when
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class OAuthAuthenticatorFactoryTest extends UnitTestSpec {

  behavior of "OAuthAuthenticatorFactory"

  private val credentialsSupplierMock = mock[AppMarketCredentialsSupplier]
  private val authorizationTokenGeneratorMock = mock[AuthorizationTokenGenerator]
  private val signatureParserMock = mock[OauthSignatureParser]

  val tested = new OAuthAuthenticatorFactory(credentialsSupplierMock, authorizationTokenGeneratorMock, signatureParserMock)


  it should "authenticate successfully if the connector generated signature matches the one of the incoming request" in {
    //Given
    val testRequestHttpMethodName = HttpGet.METHOD_NAME
    val testRequestUrl = "http://example.com"
    val testRequestClientKey = "testRequestClientKey"
    val testRequestClientSecret = "testRequestClientSecret"
    val testRequestCredentials = AppMarketCredentialsImpl(
      clientKey = testRequestClientKey,
      clientSecret = testRequestClientSecret
    )

    val incomingRequestTimestamp = "12342352341"
    val incomingRequestNonce = "testNonce"
    val incomingRequestSignature = "incomingRequestSignature"
    val testHttpCredentials = GenericHttpCredentials(
      scheme = "",
      params = Map(
        "oauth_consumer_key" -> testRequestClientKey,
        "oauth_timestamp" -> incomingRequestTimestamp,
        "oauth_nonce" -> incomingRequestNonce,
        "oauth_signature" -> incomingRequestSignature
      )
    )
    val mockConennctorGeneratedToken = "mockToken"
    
    when {
      authorizationTokenGeneratorMock.generateAuthorizationHeader(
        httpMethodName = testRequestHttpMethodName,
        resourceUrl = testRequestUrl,
        timeStamp = incomingRequestTimestamp,
        nonce = incomingRequestNonce,
        marketplaceCredentials = testRequestCredentials
      )
    } thenReturn mockConennctorGeneratedToken

    val connectorGeneratedSignatureParams = OauthParameters(
      consumerKey = testRequestClientKey,
      timestamp = incomingRequestTimestamp,
      nonce = incomingRequestNonce,
      oauthSignature = incomingRequestSignature
    )
    
    when {
      signatureParserMock.parse(mockConennctorGeneratedToken)
    } thenReturn connectorGeneratedSignatureParams
    
    when {
      credentialsSupplierMock.readCredentialsFor(testRequestClientKey)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)
    
    //When
    val authenticationResult: Future[AuthenticationResult[AppMarketCredentials]] = tested.authenticatorFunction(testRequestHttpMethodName, testRequestUrl)(Some(testHttpCredentials))
    
    //Then
    whenReady(
      future = authenticationResult,
      timeout = Timeout(5 seconds)
    ) { 
      _ shouldEqual Right(testRequestCredentials)
    }
  }

  it should "fail to authenticate if the connector generated signature does not math the one of the incoming request" in {
    //Given
    val testRequestHttpMethodName = HttpGet.METHOD_NAME
    val testRequestUrl = "http://example.com"
    val testRequestClientKey = "testRequestClientKey"
    val testRequestClientSecret = "testRequestClientSecret"
    val testRequestCredentials = AppMarketCredentialsImpl(
      clientKey = testRequestClientKey,
      clientSecret = testRequestClientSecret
    )

    val incomingRequestTimestamp = "12342352341"
    val incomingRequestNonce = "testNonce"
    val incomingRequestSignature = "incomingRequestSignature"
    val testHttpCredentials = GenericHttpCredentials(
      scheme = "",
      params = Map(
        "oauth_consumer_key" -> testRequestClientKey,
        "oauth_timestamp" -> incomingRequestTimestamp,
        "oauth_nonce" -> incomingRequestNonce,
        "oauth_signature" -> incomingRequestSignature
      )
    )
    val mockConennctorGeneratedToken = "mockToken"

    when {
      authorizationTokenGeneratorMock.generateAuthorizationHeader(
        httpMethodName = testRequestHttpMethodName,
        resourceUrl = testRequestUrl,
        timeStamp = incomingRequestTimestamp,
        nonce = incomingRequestNonce,
        marketplaceCredentials = testRequestCredentials
      )
    } thenReturn mockConennctorGeneratedToken

    val connectorGeneratedSignatureParams = OauthParameters(
      consumerKey = testRequestClientKey,
      timestamp = incomingRequestTimestamp,
      nonce = incomingRequestNonce,
      oauthSignature = incomingRequestSignature + "fsdfs"
    )

    when {
      signatureParserMock.parse(mockConennctorGeneratedToken)
    } thenReturn connectorGeneratedSignatureParams

    when {
      credentialsSupplierMock.readCredentialsFor(testRequestClientKey)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)

    //When
    val authenticationResult: Future[AuthenticationResult[AppMarketCredentials]] = tested.authenticatorFunction(testRequestHttpMethodName, testRequestUrl)(Some(testHttpCredentials))

    //Then
    whenReady(
      future = authenticationResult,
      timeout = Timeout(5 seconds)
    ) {
      _ shouldEqual Left(HttpChallenge(scheme = "OAuth", realm = None))
    }
  }

  it should "fail authentication if no credentials are provided" in {
    //Given
    val testRequestHttpMethodName = HttpGet.METHOD_NAME
    val testRequestUrl = "http://example.com"

    //When
    val authenticationResult: Future[AuthenticationResult[AppMarketCredentials]] = tested.authenticatorFunction(testRequestHttpMethodName, testRequestUrl)(None)

    //Then
    whenReady(
      future = authenticationResult,
      timeout = Timeout(5 seconds)
    ) {
      _ shouldEqual Left(HttpChallenge(scheme = "OAuth", realm = None))
    }
  }
}
