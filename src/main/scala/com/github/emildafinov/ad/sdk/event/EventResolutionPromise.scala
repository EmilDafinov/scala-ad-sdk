package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.authentication.MarketplaceCredentials
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}

case class EventReturnAddress(eventId: String, 
                              marketplaceBaseUrl: String, 
                              clientCredentials: MarketplaceCredentials)

class EventResolutionPromise[T](appMarketEventResolver: AppMarketEventResolver,
                                toAppMarketEvent: T => ApiResult,
                                eventReturnAddress: EventReturnAddress) extends EventResolver[T]{

  override def resolveWithFailure(errorMessage: String): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      resolutionHost = eventReturnAddress.marketplaceBaseUrl,
      eventId = eventReturnAddress.eventId,
      clientCredentials = eventReturnAddress.clientCredentials)(
      eventProcessingResult = ApiResults.failure(errorMessage)
    )
  
  override def resolveSuccessfully(eventResult: T): Unit = 
    appMarketEventResolver.sendEventResolvedCallback(
      resolutionHost = eventReturnAddress.marketplaceBaseUrl,
      eventId = eventReturnAddress.eventId,
      clientCredentials = eventReturnAddress.clientCredentials)(
      eventProcessingResult = toAppMarketEvent(eventResult)
    ) 
}
