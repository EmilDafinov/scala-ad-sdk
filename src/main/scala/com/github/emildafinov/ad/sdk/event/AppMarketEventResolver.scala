package com.github.emildafinov.ad.sdk.event

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.{HttpRequest, RequestEntity}
import akka.stream.Materializer
import com.github.emildafinov.ad.sdk.payload.{ApiResult, EventJsonSupport}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class AppMarketEventResolver(implicit 
                             ec: ExecutionContext, 
                             as: ActorSystem,
                             ma: Materializer) extends EventJsonSupport with StrictLogging {
  /**
    * Calls the resolve event endpoint of the AppMarket in order to notify it that an event has been resolved
    *
    * @param resolveEndpointBaseUrl Base Url of the AppMarket
    * @param eventId                the id of the event that was resolved
    * @param eventProcessingResult  the payload returned to the AppMarket
    */
  def sendEventResolvedCallback(resolveEndpointBaseUrl: String,
                                eventId: String,
                                eventProcessingResult: ApiResult): Future[Unit] = {

    for {
      requestEntity <- Marshal(eventProcessingResult).to[RequestEntity]
      response <- Http().singleRequest(
        HttpRequest(
          method = POST,
          uri = s"$resolveEndpointBaseUrl/api/integration/v1/events/$eventId/result",
          entity = requestEntity
        )
      )
    } yield
      if (response.status.isSuccess)
        logger.info(s"Successfully resolved event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
      else
        logger.error(s"Failed sending a resolution message for event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
  }
}
