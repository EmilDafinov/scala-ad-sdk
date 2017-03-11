package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentials, AuthorizationTokenGenerator, CredentialsSupplier}
import com.github.emildafinov.ad.sdk.payload.{Event, MarketInfo, UserInfo}
import com.github.emildafinov.ad.sdk.server.EventCoordinates
import com.github.emildafinov.ad.sdk.{AkkaSpec, UnitTestSpec, WiremockHttpServiceTestSuite}
import com.github.tomakehurst.wiremock.client.WireMock.{get, _}
import org.mockito.Mockito.{reset, when}

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
    val testEventCoordinates = EventCoordinates(
      clientId = testClientKey,
      eventFetchUrl = testEventUrl
    )
    when {
      mockCredentialsSuppler.readCredentialsFor(testClientKey)
    } thenThrow new RuntimeException()

    
    //Then
    a[RuntimeException] should be thrownBy {
      //When
      tested.fetchRawAppMarketEvent(testEventCoordinates)
    }
  }


  it should "parse a raw subscription order event" in {
    //Given
    val testEventId = "someEventIdHere"
    val testClientKey = "testKey"
    val testHost = s"http://localhost:${httpServerMock.port()}"
    val testHttpResource = s"/events/$testEventId"
    val testEventUrl = testHost + testHttpResource
    
    val testClientSecret = "abcdef"
    val testAppmarketCredentials = AppMarketCredentials(clientKey = testClientKey, clientSecret = testClientSecret)

    val expectedEventPayloadJson = Source.fromURL(getClass.getResource("/com/github/emildafinov/ad/sdk/event/subscription_order_event_payload.json") ).mkString
    
    when {
      mockCredentialsSuppler.readCredentialsFor(testClientKey)
    } thenReturn testAppmarketCredentials
    
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
    val testEventCoordinates = EventCoordinates(
      clientId = testClientKey,
      eventFetchUrl = testEventUrl
    )
    //When
    val (parsedEventId, parsedEvent) = tested.fetchRawAppMarketEvent(testEventCoordinates)

    //Then
    parsedEvent shouldEqual expectedEvent
    parsedEventId shouldEqual testEventId
  }
  
  //TODO: Add tests to parse all new event types !!
}
