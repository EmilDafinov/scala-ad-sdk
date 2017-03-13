package com.github.emildafinov.ad.sdk.event

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.stream.Materializer
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, CredentialsSupplier, MarketplaceCredentials, UnknownClientKeyException}
import com.github.emildafinov.ad.sdk.payload.ApiResult
import com.typesafe.scalalogging.StrictLogging
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class AppMarketEventResolver(bearerTokenGenerator: AuthorizationTokenGenerator)
                            (implicit
                             ec: ExecutionContext,
                             as: ActorSystem,
                             ma: Materializer) extends StrictLogging {

  implicit val formats = DefaultFormats
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
                                clientKey: MarketplaceCredentials,
                                eventProcessingResult: ApiResult): Future[Unit] = {

    val requestEntity = Serialization.write(eventProcessingResult)

    val request = resolveEventRequest(
        resolveEndpointBaseUrl,
        eventId,
        requestEntity,
        clientKey
      )

    Http().singleRequest(request) map { case httpResponse if httpResponse.status.isSuccess =>
        logger.info(s"Successfully resolved event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
    } recover {
      case NonFatal(_) => 
        logger.error(s"Failed sending a resolution message for event $eventId from AppMarket instance at $resolveEndpointBaseUrl")
    }
  }

  private def resolveEventRequest(resolveEndpointBaseUrl: String,
                                  eventId: String,
                                  requestEntity: String,
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
