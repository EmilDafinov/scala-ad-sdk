package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}


class SdkProvidedEventResolver[T](appMarketEventResolver: AppMarketEventResolver,
                                  toAppMarketResponse: T => ApiResult) extends EventResolver[T]{

  override def resolveWithFailure(errorMessage: String, eventReturnAddress: EventReturnAddress): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = ApiResults.unknownError(errorMessage)
    )
  
  override def resolveSuccessfully(eventResult: T, eventReturnAddress: EventReturnAddress): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = toAppMarketResponse(eventResult)
    ) 
}
