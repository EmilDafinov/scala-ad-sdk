package com.github.emildafinov.ad.sdk.event

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.Accepted
import com.github.emildafinov.ad.sdk.payload._
import com.github.emildafinov.ad.sdk.{EventHandler, UnitTestSpec}
import org.mockito.Matchers.any
import org.mockito.Mockito
import org.mockito.Mockito.{never, reset, verify, when}
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class RawEventHandlerTest extends UnitTestSpec {
  behavior of "RawEventHandler"

  val mockEventTransformer: (Event, String) => Long = mock[(Event, String) => Long]
  val mockClientHandler: EventHandler[Long, Int] = mock[EventHandler[Long, Int]]
  val mockToMarketplaceResponse: (Int) => ApiResult = mock[Int => ApiResult]
  val mockEventResolver: AppMarketEventResolver = mock[AppMarketEventResolver]

  val tested = new RawEventHandler(
    transformToClientEvent = mockEventTransformer,
    clientEventHandler = mockClientHandler,
    toMarketplaceResponse = mockToMarketplaceResponse
  )(appMarketEventResolver = mockEventResolver)

  before {
    reset(mockEventTransformer, mockClientHandler, mockToMarketplaceResponse, mockEventResolver)
  }

  it should "throw and not call the client handler if the rich event parsing fails" in {
    //Given
    val testUrl = "http://example.com"
    val testClientKey = "testClientKey"
    val testEventPayload = mock[Event]

    when {
      mockEventTransformer.apply(any(), any())
    } thenThrow classOf[MalformedRawMarketplaceEventPayloadException]


    //When
    whenReady {
      tested.processEventFrom(testEventPayload, testUrl, testClientKey).failed
    } {
      //Then
      _ shouldBe a[MalformedRawMarketplaceEventPayloadException]
    }
    verify(mockClientHandler, never())
      .handle(any())
  }


  it should "not wait for the client processing to complete before returning" in {
    //Given
    val testEventFetchUrl = "http://example.com/events/someEventIdHere"
    val testClientKey = "testClientKey"

    val testEvent = Event(
      `type` = "type",
      marketplace = MarketInfo("testPartner", "http://example.com"),
      creator = UserInfo()
    )

    when {
      mockClientHandler.handle(any())
    } thenAnswer { _ =>
      Thread.sleep(Long.MaxValue) //Wait a very long time
      1
    }

    //When
    whenReady(
      future = tested.processEventFrom(testEvent, testEventFetchUrl, testClientKey), 
      timeout = Timeout(1 second)
    ) { result =>
      //Then
      result shouldEqual HttpResponse(status = Accepted)

      verify(mockClientHandler, Mockito.atMost(1))
        .handle(any())
    }
  }
}
