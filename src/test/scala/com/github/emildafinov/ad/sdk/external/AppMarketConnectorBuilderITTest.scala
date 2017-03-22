package com.github.emildafinov.ad.sdk.external

import java.util.Optional

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.StatusCodes.Accepted
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpHeader, HttpRequest}
import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentials, AppMarketCredentialsImpl, AppMarketCredentialsSupplier, AuthorizationTokenGenerator}
import com.github.emildafinov.ad.sdk.event.payloads.{AddonSubscriptionOrder, SubscriptionCancel, SubscriptionOrder}
import com.github.emildafinov.ad.sdk.{AkkaSpec, EventHandler, UnitTestSpec, WiremockHttpServiceTestSuite}
import com.github.tomakehurst.wiremock.client.WireMock._
import org.mockito.{Matchers, Mockito}
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import com.github.emildafinov.ad.sdk.util.readResourceFile

class AppMarketConnectorBuilderITTest extends UnitTestSpec
  with AkkaSpec
  with WiremockHttpServiceTestSuite {

  behavior of "AppMarketConnector"

  private val subscriptionOrderHandlerMock = mock[EventHandler[SubscriptionOrder]]

  private val subscriptionCancelHandlerMock = mock[EventHandler[SubscriptionCancel]]

  private val subscriptionOrderAddonHandlerMock = mock[EventHandler[AddonSubscriptionOrder]]

  private val credentialsSupplierMock = mock[AppMarketCredentialsSupplier]

  private val tokenGenerator = new AuthorizationTokenGenerator

  private val connector = new AppMarketConnectorBuilder(
    subscriptionOrderHandlerMock,
    subscriptionCancelHandlerMock,
    credentialsSupplierMock
  )
    .addonSubscriptionOrderHandler(subscriptionOrderAddonHandlerMock)
    .build()
    .start()


  it should "trigger the Subscription Order Handler" in {

    //Given
    val testClientId = "testClientId"
    val testClientSecret = "testClientSecret"

    val testRequestCredentials =
      AppMarketCredentialsImpl(
        clientKey = testClientId,
        clientSecret = testClientSecret
      )

    Mockito.when {
      credentialsSupplierMock.readCredentialsFor(testClientId)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)

    val testEventId = "abcde"
    val testEventPayloadResource = s"/integration/$testEventId"
    val testEventPayloadFullUrl = s"http://127.0.0.1:${httpServerMock.port()}" + testEventPayloadResource
    val testConnectorUrl = s"http://127.0.0.1:8000/integration/subscription/order?eventUrl=$testEventPayloadFullUrl"
    httpServerMock
      .stubFor(
        get(urlPathEqualTo(testEventPayloadResource))
          .withHeader("Authorization", containing("OAuth"))
          .willReturn {
            aResponse()
              .withHeader("Content-Type", "application/json")
              .withBody(readResourceFile("/com/github/emildafinov/ad/sdk/event/subscription_order_event_payload.json"))
              .withStatus(200)
          }
      )

    val headerValue = tokenGenerator.generateAuthorizationHeader(
      httpMethodName = GET.value,
      resourceUrl = testConnectorUrl,
      marketplaceCredentials = testRequestCredentials
    )

    val authHeader: HttpHeader = RawHeader("Authorization", headerValue)
    val testRequest = HttpRequest(
      method = GET,
      uri = testConnectorUrl,
      headers = scala.collection.immutable.Seq(authHeader)

    )

    //When
    whenReady(
      future = Http().singleRequest(testRequest),
      timeout = Timeout(5 seconds)
    ) { response =>
      response.status shouldEqual Accepted
      Mockito
        .verify(subscriptionOrderHandlerMock).handle(Matchers.any(), Matchers.any())
    }
  }

  it should "trigger the Subscription Cancel Handler" in {

    //Given
    val testClientId = "testClientId"
    val testClientSecret = "testClentSecret"

    val testRequestCredentials =
      AppMarketCredentialsImpl(
        clientKey = testClientId,
        clientSecret = testClientSecret
      )

    Mockito.when {
      credentialsSupplierMock.readCredentialsFor(testClientId)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)

    val testEventId = "abcde"
    val testEventPayloadResource = s"/integration/$testEventId"
    val testEventPayloadFullUrl = s"http://127.0.0.1:${httpServerMock.port()}" + testEventPayloadResource
    val testConnectorUrl = s"http://127.0.0.1:8000/integration/subscription/cancel?eventUrl=$testEventPayloadFullUrl"
    httpServerMock
      .stubFor(
        get(urlPathEqualTo(testEventPayloadResource))
          .withHeader("Authorization", containing("OAuth"))
          .willReturn {
            aResponse()
              .withHeader("Content-Type", "application/json")
              .withBody(readResourceFile("/com/github/emildafinov/ad/sdk/event/subscription_cancel_event_payload.json"))
              .withStatus(200)
          }
      )

    val headerValue = tokenGenerator.generateAuthorizationHeader(
      httpMethodName = GET.value,
      resourceUrl = testConnectorUrl,
      marketplaceCredentials = testRequestCredentials
    )

    val authHeader: HttpHeader = RawHeader("Authorization", headerValue)
    val testRequest = HttpRequest(
      method = GET,
      uri = testConnectorUrl,
      headers = scala.collection.immutable.Seq(authHeader)

    )

    //When
    whenReady(
      future = Http().singleRequest(testRequest),
      timeout = Timeout(5 seconds)
    ) { response =>
      response.status shouldEqual Accepted
      Mockito
        .verify(subscriptionCancelHandlerMock).handle(Matchers.any(), Matchers.any())
    }
  }

  it should "trigger the Subscription Addon Order Handler" in {

    //Given
    val testClientId = "testClientId"
    val testClientSecret = "testClentSecret"

    val testRequestCredentials =
      AppMarketCredentialsImpl(
        clientKey = testClientId,
        clientSecret = testClientSecret
      )

    Mockito.when {
      credentialsSupplierMock.readCredentialsFor(testClientId)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)

    val testEventId = "abcde"
    val testEventPayloadResource = s"/integration/$testEventId"
    val testEventPayloadFullUrl = s"http://127.0.0.1:${httpServerMock.port()}" + testEventPayloadResource
    val testConnectorUrl = s"http://127.0.0.1:8000/integration/subscription/order?eventUrl=$testEventPayloadFullUrl"
    httpServerMock
      .stubFor(
        get(urlPathEqualTo(testEventPayloadResource))
          .withHeader("Authorization", containing("OAuth"))
          .willReturn {
            aResponse()
              .withHeader("Content-Type", "application/json")
              .withBody(readResourceFile("/com/github/emildafinov/ad/sdk/event/subscription_order_addon_payload.json"))
              .withStatus(200)
          }
      )

    val headerValue = tokenGenerator.generateAuthorizationHeader(
      httpMethodName = GET.value,
      resourceUrl = testConnectorUrl,
      marketplaceCredentials = testRequestCredentials
    )

    val authHeader: HttpHeader = RawHeader("Authorization", headerValue)
    val testRequest = HttpRequest(
      method = GET,
      uri = testConnectorUrl,
      headers = scala.collection.immutable.Seq(authHeader)

    )

    //When
    whenReady(
      future = Http().singleRequest(testRequest),
      timeout = Timeout(5 seconds)
    ) { response =>
      response.status shouldEqual Accepted
      Mockito
        .verify(subscriptionCancelHandlerMock).handle(Matchers.any(), Matchers.any())
    }
  }

  it should "trigger the 'Unimplemented Event' handler " in {

    //Given
    val testClientId = "testClientId"
    val testClientSecret = "testClentSecret"

    val testRequestCredentials =
      AppMarketCredentialsImpl(
        clientKey = testClientId,
        clientSecret = testClientSecret
      )

    Mockito.when {
      credentialsSupplierMock.readCredentialsFor(testClientId)
    } thenReturn Optional.of[AppMarketCredentials](testRequestCredentials)

    val testEventId = "abcde"
    val testEventPayloadResource = s"/integration/$testEventId"
    val testEventPayloadFullUrl = s"http://127.0.0.1:${httpServerMock.port()}" + testEventPayloadResource
    val testConnectorUrl = s"http://127.0.0.1:8000/integration/subscription/change?eventUrl=$testEventPayloadFullUrl"
    httpServerMock
      .stubFor(
        get(urlPathEqualTo(testEventPayloadResource))
          .withHeader("Authorization", containing("OAuth"))
          .willReturn {
            aResponse()
              .withHeader("Content-Type", "application/json")
              .withBody(readResourceFile("/com/github/emildafinov/ad/sdk/event/subscription_order_addon_payload.json"))
              .withStatus(200)
          }
      )

    val headerValue = tokenGenerator.generateAuthorizationHeader(
      httpMethodName = GET.value,
      resourceUrl = testConnectorUrl,
      marketplaceCredentials = testRequestCredentials
    )

    val authHeader: HttpHeader = RawHeader("Authorization", headerValue)
    val testRequest = HttpRequest(
      method = GET,
      uri = testConnectorUrl,
      headers = scala.collection.immutable.Seq(authHeader)

    )

    //When
    whenReady(
      future = Http().singleRequest(testRequest),
      timeout = Timeout(5 seconds)
    ) { response =>
      response.status shouldEqual Accepted
    }
  }
}
