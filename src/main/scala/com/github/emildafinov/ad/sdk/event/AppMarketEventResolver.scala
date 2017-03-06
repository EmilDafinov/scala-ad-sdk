package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.payload.{ApiResult, EventJsonSupport}

import scala.concurrent.Future

class AppMarketEventResolver extends EventJsonSupport {
  /**
    * Calls the resolve event endpoint of the AppMarket in order to notify it that an event has been resolved
    * @param resolveEndpointBaseUrl Base Url of the AppMarket
    * @param eventId the id of the event that was resolved
    * @param eventProcessingResult the payload returned to the AppMarket
    */
  def sendEventResolvedCallback(resolveEndpointBaseUrl: String, eventId: String, eventProcessingResult: ApiResult): Future[Unit] = {
//    Marshal(eventProcessingResult).to[RequestEntity]
//      .flatMap { requestEntity =>
//        Http().singleRequest(
//          HttpRequest(
//            method = POST,
//            uri = s"$resolveEndpointBaseUrl/api/integration/v1/events/$eventId/result",
//            entity = requestEntity
//          )
//        )
//      }
    ???
  }
}
