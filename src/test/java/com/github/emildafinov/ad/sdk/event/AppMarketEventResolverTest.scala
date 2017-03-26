package com.github.emildafinov.ad.sdk.event

import java.util.Optional

import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentials, AppMarketCredentialsImpl, AppMarketCredentialsSupplier, AuthorizationTokenGenerator}
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.ApiResults
import com.github.emildafinov.ad.sdk.{AkkaSpec, EventReturnAddressImpl, UnitTestSpec, WiremockHttpServiceTestSuite}
import com.github.tomakehurst.wiremock.client.WireMock._
import org.mockito.Mockito.when
import org.scalatest.concurrent.PatienceConfiguration.Timeout

class AppMarketEventResolverTest extends UnitTestSpec with AkkaSpec with WiremockHttpServiceTestSuite {

  behavior of "AppMarketEventResolver"

  val credentialsSupplier: AppMarketCredentialsSupplier = mock[AppMarketCredentialsSupplier]

  val authorizationTokenGenerator: AuthorizationTokenGenerator = mock[AuthorizationTokenGenerator]

  val tested = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)

  it should "send a failed `event resolved` callback" in {
    //Given
    val testEventId = "1234qwer"
    val testEventProcessingResult = ApiResults.unknownError()
    val testKey = "testKey"
    val testSecret = "testSecret"
    val testEventResolutionEndpoint = s"http://localhost:${httpServerMock.port()}"

    val testClientCredentials = AppMarketCredentialsImpl(
      clientKey = testKey,
      clientSecret = testSecret
    )

    when {
      credentialsSupplier.readCredentialsFor(testKey)
    } thenReturn Optional.of[AppMarketCredentials](testClientCredentials)

    when {
      authorizationTokenGenerator.generateAuthorizationHeader(
        httpMethodName = "POST",
        resourceUrl = testEventResolutionEndpoint + "/api/integration/v1/events/1234qwer/result",
        marketplaceCredentials = testClientCredentials
      )
    } thenReturn
      s"""OAuth oauth_consumer_key="$testKey", oauth_nonce="abcder", oauth_signature="fgbhndr6yhdrtgf", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1404540540", oauth_version="1.0""""

    httpServerMock.
      stubFor(
        post(urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result"))
          .withHeader("Authorization", containing("OAuth"))
          .willReturn {
            aResponse().withStatus(200)
          }
      )

    //When
    val eventNotificationFuture = tested.sendEventResolvedCallback(
      new EventReturnAddressImpl(
        testEventId,
        testEventResolutionEndpoint,
        testClientCredentials.clientKey
      ),
      eventProcessingResult = testEventProcessingResult
    )

    //Then
    whenReady(future = eventNotificationFuture, 
              timeout = Timeout(5 seconds)) { _ =>
      httpServerMock
        .verify(
          postRequestedFor(
            urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result")
          )
        )
    }
  }

  it should "send a successful `event resolved` callback" in {
    //Given
    val testEventId = "1234qwer"
    val testEventProcessingResult = ApiResults.success()
    val testClientKey = "testClientKey"
    val testSecret = "testSecret"
    val testEventResolutionEndpoint = s"http://localhost:${httpServerMock.port()}"

    val testClientCredentials = AppMarketCredentialsImpl(
      clientKey = testClientKey,
      clientSecret = testSecret
    )
    when {
      credentialsSupplier.readCredentialsFor(testClientKey)
    } thenReturn Optional.of[AppMarketCredentials](testClientCredentials)

    val expectedOauthToken = s"""OAuth oauth_consumer_key="$testClientKey", oauth_nonce="abcder", oauth_signature="fgbhndr6yhdrtgf", oauth_signature_method="HMAC-SHA1", oauth_timestamp="1404540540", oauth_version="1.0""""
    when {
      authorizationTokenGenerator.generateAuthorizationHeader(
        httpMethodName = "POST",
        resourceUrl = testEventResolutionEndpoint + "/api/integration/v1/events/1234qwer/result",
        marketplaceCredentials = testClientCredentials
      )
    } thenReturn
      expectedOauthToken

    httpServerMock
      .stubFor(
        post(urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result"))
            .withHeader("Authorization", containing("OAuth"))
          .willReturn(
            aResponse().withStatus(200)
          )
      )

    //When
    val eventNotificationFuture = tested.sendEventResolvedCallback(
      new EventReturnAddressImpl(
        testEventId,
        s"http://localhost:${httpServerMock.port()}",
        testClientCredentials.clientKey
      ),
      eventProcessingResult = testEventProcessingResult
    )

    //Then
    whenReady(future = eventNotificationFuture, 
              timeout = Timeout(5 seconds)) { _ =>
      httpServerMock
        .verify(
          postRequestedFor(
            urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result")
          ) withHeader("Authorization", equalTo(expectedOauthToken))
        )
    }
  }
}
