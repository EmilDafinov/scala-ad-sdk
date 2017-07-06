package com.github.emildafinov.ad.sdk.event

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.Accepted
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.EventType.SUBSCRIPTION_ORDER
import com.github.emildafinov.ad.sdk.payload.NoticeType.CLOSED
import com.github.emildafinov.ad.sdk.payload._
import com.github.emildafinov.ad.sdk.http.server.RawEventHandler
import com.github.emildafinov.ad.sdk.{EventHandler, UnitTestSpec}
import org.mockito.Matchers.any
import org.mockito.Mockito
import org.mockito.Mockito.{reset, verify, when}
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

class RawEventHandlerTest extends UnitTestSpec {
  behavior of "RawEventHandler"

  val mockEventTransformer: (Event, String) => Long = mock[(Event, String) => Long]
  val mockClientHandler: EventHandler[Long] = mock[EventHandler[Long]]
  val mockEventResolver: AppMarketEventResolver = mock[AppMarketEventResolver]

  val tested = new RawEventHandler(
    transformToClientEvent = mockEventTransformer,
    clientEventHandler = mockClientHandler
  )

  before {
    reset(mockEventTransformer, mockClientHandler, mockEventResolver)
  }

  it should "not wait for the client processing to complete before returning" in {
    //Given
    val testEventId = "eventId"
    val testClientKey = "testClientKey"
    val testEvent = Event(
      `type` = SUBSCRIPTION_ORDER,
      marketplace = Marketplace("testPartner", "http://example.com"),
      creator = User(),
      payload =
        Payload(
          company = null,
          account = Option(
            Account(
              parentAccountIdentifier = Option("")
            )
          ),
          notice = Option(
            Notice(
              `type` = CLOSED
            )
          )
        )
    )

    when {
      mockClientHandler.handle(any(), any())
    } thenAnswer { _ =>
      while (true) {
        // Loop forever
      }
      ???
    }

    //When
    whenReady(
      future = tested.processRawEvent(testEventId, testEvent, testClientKey),
      timeout = Timeout(2 seconds)
    ) { result =>
      //Then
      result shouldEqual HttpResponse(status = Accepted)

      verify(mockClientHandler, Mockito.atMost(1))
        .handle(any(), any())
    }
  }
}
