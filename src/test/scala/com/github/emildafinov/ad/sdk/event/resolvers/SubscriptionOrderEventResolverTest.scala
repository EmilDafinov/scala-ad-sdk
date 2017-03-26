package com.github.emildafinov.ad.sdk.event.resolvers

import java.util.Optional

import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentials, AppMarketCredentialsImpl, AppMarketCredentialsSupplier}
import com.github.emildafinov.ad.sdk.payload.SubscriptionResponseEvent
import com.github.emildafinov.ad.sdk._
import com.github.tomakehurst.wiremock.client.WireMock._
import org.mockito.Mockito.when
import com.github.emildafinov.ad.sdk.util.readResourceFile

import scala.concurrent.Await

class SubscriptionOrderEventResolverTest extends UnitTestSpec
  with ConnectorServerAkkaDependenciesModule with WiremockHttpServiceTestSuite {

  behavior of "SubscriptionOrderEventResolver"
  private val testCredentialsSupplier = mock[AppMarketCredentialsSupplier]

  private val tested = SubscriptionOrderEventResolver(testCredentialsSupplier)

  it should "send an 'order successful' Json response when resolving an event successfully" in {
    //Given
    val testEventId = "testEventId"
    val testBaseUrl = s"http://127.0.0.1:${httpServerMock.port()}"
    val testClientId = "testClientId"
    val testClientSecret = "testClientSecret"

    val testCredentials = AppMarketCredentialsImpl(
      clientKey = testClientId,
      clientSecret = testClientSecret
    )
    val testEventAddress = new EventReturnAddressImpl(
      testEventId, testBaseUrl, testClientId
    )
    val expectedUserIdentifier = "expectedUserIdentifier"
    val expectedAccountIdentifier = "expectedAccountIdentifier"

    val testSubscriptionOrderResponse = SubscriptionResponseEvent(
      expectedUserIdentifier, expectedAccountIdentifier
    )
    when {
      testCredentialsSupplier.readCredentialsFor(testClientId)  
    } thenReturn Optional.of[AppMarketCredentials](testCredentials)

    //When
    tested.resolveSuccessfully(testSubscriptionOrderResponse, testEventAddress)

    //Then
    Thread.sleep(1000)
    httpServerMock
      .verify {
        postRequestedFor(urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result"))
        .withRequestBody(equalToJson(readResourceFile("/com/github/emildafinov/ad/sdk/event/resolvers/subscription_order_event_success_response.json")))  
      }
  }

  it should "send an 'order failed' Json response when resolving an event with failure" in {
    //Given
    val testEventId = "testEventId"
    val testBaseUrl = s"http://127.0.0.1:${httpServerMock.port()}"
    val testClientId = "testClientId"
    val testClientSecret = "testClientSecret"

    val testCredentials = AppMarketCredentialsImpl(
      clientKey = testClientId,
      clientSecret = testClientSecret
    )
    val testEventAddress = new EventReturnAddressImpl(
      testEventId, testBaseUrl, testClientId
    )

    val testSubscriptionOrderResponse = "Order failed"
    
    when {
      testCredentialsSupplier.readCredentialsFor(testClientId)
    } thenReturn Optional.of[AppMarketCredentials](testCredentials)

    //When
    val resolutionSentFuture = tested.resolveWithFailure(testSubscriptionOrderResponse, testEventAddress)

    //Then
    Await.ready(
      awaitable = resolutionSentFuture, 
      atMost = 10 seconds
    )

    httpServerMock
      .verify {
        postRequestedFor(urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result"))
          .withRequestBody(equalToJson(readResourceFile("/com/github/emildafinov/ad/sdk/event/resolvers/subscription_order_event_failure_response.json")))
      }
  }
}
