package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentials
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.{eq => mockEq}
import org.mockito.Mockito.verify

class EventResolutionPromiseTest extends UnitTestSpec {

  behavior of "EventResolutionPromise"

  it should "call the underlying event resolver with a failure payload" in {
    //Given
    val appMarketEventResolverMock = mock[AppMarketEventResolver]
    val mockMarshaller = (any: String) => ApiResults.success()
    val testResolutionHost = "http://example.com"
    val testEventId = "eventId"
    val testCredentials = AppMarketCredentials(
      clientKey = "clientKey",
      clientSecret = "clientSecret"
    )
    val testReturnAddress = EventReturnAddress(
      eventId = testEventId,
      marketplaceBaseUrl = testResolutionHost,
      clientCredentials = testCredentials
    )
    val testErrorMessage = "errorMessage"
    val returnMessageCaptor = ArgumentCaptor.forClass(classOf[ApiResult])
    val tested = new EventResolutionPromise[String](appMarketEventResolverMock, mockMarshaller, testReturnAddress)

    //When
    tested.resolveWithFailure(testErrorMessage)
    
    //Then
    verify(appMarketEventResolverMock)
        .sendEventResolvedCallback(
          mockEq(testReturnAddress),
          returnMessageCaptor.capture())
    
    returnMessageCaptor.getValue.success shouldEqual false
  }
}
