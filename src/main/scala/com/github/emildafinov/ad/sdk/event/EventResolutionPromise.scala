package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}


class EventResolutionPromise[T](appMarketEventResolver: AppMarketEventResolver,
                                toAppMarketResponse: T => ApiResult,
                                eventReturnAddress: EventReturnAddress) extends EventResolver[T]{

  override def resolveWithFailure(errorMessage: String): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = ApiResults.unknownError(errorMessage)
    )
  
  override def resolveSuccessfully(eventResult: T): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = toAppMarketResponse(eventResult)
    ) 
}
