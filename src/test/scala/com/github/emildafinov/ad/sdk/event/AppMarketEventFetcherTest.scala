package com.github.emildafinov.ad.sdk.event

import java.util.Optional

import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentials, AuthorizationTokenGenerator, CredentialsSupplier, MarketplaceCredentials}
import com.github.emildafinov.ad.sdk.payload.{Event, MarketInfo, UserInfo}
import com.github.emildafinov.ad.sdk.{AkkaSpec, UnitTestSpec, WiremockHttpServiceTestSuite}
import com.github.tomakehurst.wiremock.client.WireMock.{get, _}
import org.mockito.Mockito.{reset, when}
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import scala.concurrent.Await
import scala.concurrent.duration.Duration.Inf
import scala.io.Source
import scala.language.postfixOps

class AppMarketEventFetcherTest 
  extends UnitTestSpec 
    with AkkaSpec 
    with WiremockHttpServiceTestSuite {

  behavior of "AppMarketEventFetcher"

  private val mockCredentialsSuppler = mock[CredentialsSupplier]
  private val mockAuthorizationTokenGenerator = mock[AuthorizationTokenGenerator]

  val tested: AppMarketEventFetcher = new AppMarketEventFetcher(credentialsSupplier = mockCredentialsSuppler, authorizationTokenGenerator = mockAuthorizationTokenGenerator)

  before {
    reset(mockCredentialsSuppler, mockAuthorizationTokenGenerator)
  }

  it should "throw an appropriate exception when no credentials are found for the key" in {
    //Given
    val testEventUrl = dummyUrl
    val testClientKey = "testKey"

    when {
      mockCredentialsSuppler.readCredentialsFor(testClientKey)
    } thenReturn Optional.empty[MarketplaceCredentials]

    //When
    val futureEvent = tested.fetchRawAppMarketEvent(
      eventFetchUrl = testEventUrl,
      clientKey = testClientKey
    )
    //Then
    a[CouldNotFetchRawMarketplaceEventException] should be thrownBy {
      Await.result(futureEvent, atMost = Inf)
    }
  }
  
  it should "parse a raw subscription order event" in {
    //Given
    val testClientKey = "testKey"
    val testHost = s"http://localhost:${httpServerMock.port()}"
    val testHttpResource = "/events/someEventIdHere"
    val testEventUrl = testHost + testHttpResource
    
    val testClientSecret = "abcdef"
    val testAppmarketCredentials = AppMarketCredentials(clientKey = testClientKey, clientSecret = testClientSecret)

    val expectedEventPayloadJson = Source.fromURL(getClass.getResource("/com/github/emildafinov/ad/sdk/event/subscription_order_event_payload.json") ).mkString
    
    when {
      mockCredentialsSuppler.readCredentialsFor(testClientKey)
    } thenReturn Optional.of[MarketplaceCredentials](
      testAppmarketCredentials
    )
    
    when {
      mockAuthorizationTokenGenerator.generateAuthorizationHeader(
        "GET",testEventUrl, testAppmarketCredentials
      )
    } thenReturn "afscgg"
    
    httpServerMock
      .givenThat {
        get(urlEqualTo(testHttpResource)) willReturn {
          aResponse withBody expectedEventPayloadJson
        }
      }
    val expectedEvent = Event(
      `type` = "SUBSCRIPTION_ORDER",
      marketplace = MarketInfo(
        partner = "APPDIRECT",
        baseUrl = "http://sample.appdirect.com:8888"
      ),
      creator = UserInfo()
    )

    //When
    val parsedEventFuture = tested.fetchRawAppMarketEvent(
      eventFetchUrl = testEventUrl,
      clientKey = testClientKey
    )

    //Then
    whenReady(
      future = parsedEventFuture,
      timeout = Timeout(5 seconds)
    ) { result =>
      result shouldEqual expectedEvent
    }
  }
  
  //TODO: Add tests to parse all new event types !!
}
