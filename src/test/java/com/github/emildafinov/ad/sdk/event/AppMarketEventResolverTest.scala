package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.payload.ApiResults
import com.github.emildafinov.ad.sdk.{AkkaSpec, UnitTestSpec, WiremockHttpServiceTestSuite}
import com.github.tomakehurst.wiremock.client.WireMock.{aResponse, post, postRequestedFor, urlPathEqualTo}
import org.scalatest.concurrent.PatienceConfiguration.Timeout

class AppMarketEventResolverTest extends UnitTestSpec with AkkaSpec with WiremockHttpServiceTestSuite {

  behavior of "AppMarketEventResolver"

  val tested = new AppMarketEventResolver()

  it should "send the `event resolved` callback" in {
    //Given
    val testEventId = "1234qwer"
    val testEventProcessingResult = ApiResults.unknownError()
    httpServerMock.
      givenThat(
        post(urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result")) willReturn {
          aResponse().withStatus(200)
        }
      )

    //When
    val eventNotificationFuture = tested.sendEventResolvedCallback(
      resolveEndpointBaseUrl = s"http://localhost:${httpServerMock.port()}",
      eventId = testEventId,
      eventProcessingResult = testEventProcessingResult
    )

    //Then
    whenReady(
      future = eventNotificationFuture,
      timeout = Timeout(5 seconds)
    ) { _ =>
      httpServerMock
        .verify(
          postRequestedFor(
            urlPathEqualTo(s"/api/integration/v1/events/$testEventId/result")
          )
        )
    }
  }
}
