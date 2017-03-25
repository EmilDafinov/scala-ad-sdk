package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.Future


class SdkProvidedEventResolver[T](appMarketEventResolver: AppMarketEventResolver,
                                  toAppMarketResponse: T => ApiResult) extends EventResolver[T] with StrictLogging {

  override def resolveWithFailure(errorMessage: String, eventReturnAddress: EventReturnAddress): Future[Unit] = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = ApiResults.unknownError(errorMessage)
    )
  
  override def resolveSuccessfully(eventResult: T, eventReturnAddress: EventReturnAddress): Future[Unit] = 
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = toAppMarketResponse(eventResult)
    ) 
}
