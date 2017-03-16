package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.authentication.MarketplaceCredentials
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}

/**
  * Defines the information necessary to locate the event resolution endpoint
  * for an AppMarket event
  * @param eventId the id of the event
  * @param marketplaceBaseUrl the url of the host where the event was sent from
  * @param clientCredentials the credentials needed to authenticate with the host
  */
case class EventReturnAddress(eventId: String, 
                              marketplaceBaseUrl: String, 
                              clientCredentials: MarketplaceCredentials)

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
