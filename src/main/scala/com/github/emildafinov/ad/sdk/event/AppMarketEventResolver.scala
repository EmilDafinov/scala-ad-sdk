package com.github.emildafinov.ad.sdk.event

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.http.scaladsl.model.{HttpRequest, RequestEntity}
import akka.stream.Materializer
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, CredentialsSupplier, MarketplaceCredentials}
import com.github.emildafinov.ad.sdk.payload.{ApiResult, EventJsonSupport}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContext, Future}

class AppMarketEventResolver(bearerTokenGenerator: AuthorizationTokenGenerator,
                             credentialsSupplier: CredentialsSupplier)
                            (implicit
                             ec: ExecutionContext,
                             as: ActorSystem,
                             ma: Materializer) extends EventJsonSupport with StrictLogging {
  /**
    * Calls the resolve event endpoint of the AppMarket in order to notify it that an event has been resolved
    *
    * @param resolveEndpointBaseUrl Base Url of the AppMarket
    * @param eventId                the id of the event that was resolved
    * @param clientKey              the client key used to sign the event resolved message                              
    * @param eventProcessingResult  the payload returned to the AppMarket
    */
  def sendEventResolvedCallback(resolveEndpointBaseUrl: String,
                                eventId: String,
                                clientKey: String,
                                eventProcessingResult: ApiResult): Future[Unit] = {

    for {
      requestEntity <- Marshal(eventProcessingResult).to[RequestEntity]
      request = resolveEventRequest(
        resolveEndpointBaseUrl,
        eventId,
        requestEntity,
        credentialsSupplier.readCredentialsFor(clientKey)
      )
      response <- Http().singleRequest(request)
    } yield if (response.status.isSuccess)
        logger.info(s"Successfully resolved event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
      else
        logger.error(s"Failed sending a resolution message for event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
  }

  private def resolveEventRequest(resolveEndpointBaseUrl: String,
                                  eventId: String,
                                  requestEntity: RequestEntity,
                                  clientCredentials: MarketplaceCredentials) = {
    val resourceUrl = s"$resolveEndpointBaseUrl/api/integration/v1/events/$eventId/result"
    val authorizationHeader = Authorization(
      OAuth2BearerToken(
        bearerTokenGenerator.generateAuthorizationHeader(
          httpMethodName = "POST",
          resourceUrl = resourceUrl,
          marketplaceCredentials = clientCredentials
        )
      )
    )

    HttpRequest(
      method = POST,
      uri = resourceUrl,
      entity = requestEntity,
      headers = List(authorizationHeader)
    )
  }
}
