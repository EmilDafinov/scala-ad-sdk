package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.{EventReturnAddressImpl, UnitTestSpec}
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsImpl
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.{eq => mockEq}
import org.mockito.Mockito.verify

class SdkProvidedEventResolverTest extends UnitTestSpec {

  behavior of "EventResolutionPromise"

  it should "call the underlying event resolver with a failure payload" in {
    //Given
    val appMarketEventResolverMock = mock[AppMarketEventResolver]
    val mockMarshaller = (any: String) => ApiResults.success()
    val testResolutionHost = "http://example.com"
    val testEventId = "eventId"
    val testCredentials = AppMarketCredentialsImpl(
      clientKey = "clientKey",
      clientSecret = "clientSecret"
    )
    val testReturnAddress = new EventReturnAddressImpl(
      testEventId,
      testResolutionHost,
      testCredentials.clientKey
    )
    val testErrorMessage = "errorMessage"
    val returnMessageCaptor = ArgumentCaptor.forClass(classOf[ApiResult])
    val tested = new SdkProvidedEventResolver[String](appMarketEventResolverMock, mockMarshaller)

    //When
    tested.resolveWithFailure(testErrorMessage, testReturnAddress)
    
    //Then
    verify(appMarketEventResolverMock)
        .sendEventResolvedCallback(
          mockEq(testReturnAddress),
          returnMessageCaptor.capture())
    
    returnMessageCaptor.getValue.success shouldEqual false
  }
}
