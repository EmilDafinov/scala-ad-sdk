package com.github.emildafinov.ad.sdk.event

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.stream.Materializer
import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, AppMarketCredentialsSupplier, AppMarketCredentials, UnknownClientKeyException}
import com.github.emildafinov.ad.sdk.payload.ApiResult
import com.typesafe.scalalogging.StrictLogging
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class AppMarketEventResolver(bearerTokenGenerator: AuthorizationTokenGenerator, 
                             credentialsSupplier: AppMarketCredentialsSupplier)
                            (implicit
                             ec: ExecutionContext,
                             as: ActorSystem,
                             ma: Materializer) extends StrictLogging {

  implicit val formats = DefaultFormats
  /**
    * Calls the resolve event endpoint of the AppMarket in order to notify it that an event has been resolved
    *     
    * @param eventProcessingResult  the payload returned to the AppMarket
    */
  def sendEventResolvedCallback(eventReturnAddress: EventReturnAddress,
                               eventProcessingResult: ApiResult): Future[Unit] = {


    val request = resolveEventRequest(
        eventReturnAddress.marketplaceBaseUrl,
        eventReturnAddress.eventId,
        eventProcessingResult,
        credentialsSupplier.readCredentialsFor(eventReturnAddress.clientId()).orElseThrow(() => new IllegalArgumentException())
      )

    Http().singleRequest(request) map { case httpResponse if httpResponse.status.isSuccess =>
        logger.info(s"Successfully resolved event ${eventReturnAddress.eventId} from AppMarket instance at ${eventReturnAddress.marketplaceBaseUrl}")
    } recover {
      case NonFatal(_) => 
        logger.error(s"Failed sending a resolution message for event ${eventReturnAddress.eventId} from AppMarket instance at ${eventReturnAddress.marketplaceBaseUrl}")
    }
  }

  private def resolveEventRequest(resolveEndpointBaseUrl: String,
                                  eventId: String,
                                  requestBody: ApiResult,
                                  clientCredentials: AppMarketCredentials) = {
    
    val resourceUrl = s"$resolveEndpointBaseUrl/api/integration/v1/events/$eventId/result"
    val requestEntity = Serialization.write(requestBody)
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
