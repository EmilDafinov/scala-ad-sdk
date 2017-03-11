package com.github.emildafinov.ad.sdk.event

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{Authorization, OAuth2BearerToken}
import akka.stream.Materializer
import akka.util.ByteString
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, CredentialsSupplier, MarketplaceCredentials}
import com.github.emildafinov.ad.sdk.payload.{Event, EventJsonSupport}
import com.github.emildafinov.ad.sdk.server.EventCoordinates
import spray.json._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.control.NonFatal

/**
  * Returns the event id and payload.
  * Synchoronous, because we always want to retrieve the event before releasing the connections
  * @param credentialsSupplier         for retrieving the client secret needed to generate the bearer token
  * @param authorizationTokenGenerator used to generate the OAuth bearer token used to sign the request
  * @param appMarketTimeoutInterval    timeout for retrieving the event payload.
  * @param as
  * @param am
  * @param ec
  */
class AppMarketEventFetcher(credentialsSupplier: CredentialsSupplier,
                            authorizationTokenGenerator: AuthorizationTokenGenerator,
                            appMarketTimeoutInterval: FiniteDuration = 15 seconds)
                           (implicit as: ActorSystem,
                            am: Materializer,
                            ec: ExecutionContext) extends EventJsonSupport {

  def fetchRawAppMarketEvent(eventCoordinates: EventCoordinates): (String, Event) = {

    val parsedRawEvent = (for {
      eventFetchRequest <- signedFetchRequest(eventCoordinates.eventFetchUrl, eventCoordinates.clientId)
      response <- Http().singleRequest(eventFetchRequest)
      responseBody <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
      responseBodyString = responseBody.decodeString("utf8")
    } yield responseBodyString
      .parseJson
      .convertTo[Event]) map { eventPayload =>

      extractIdFrom(eventCoordinates.eventFetchUrl) -> eventPayload

    } recover {
      case NonFatal(_) => throw new CouldNotFetchRawMarketplaceEventException()
    }

    Await.result(
      awaitable = parsedRawEvent,
      atMost = appMarketTimeoutInterval
    )
  }

  private def signedFetchRequest(eventFetchUrl: String, clientKey: String) = Future {
    val marketplaceCredentials = credentialsSupplier.readCredentialsFor(clientKey)
    val bearerTokenValue = authorizationTokenGenerator.generateAuthorizationHeader(
      "GET",
      eventFetchUrl,
      marketplaceCredentials
    )

    HttpRequest(
      method = GET,
      uri = eventFetchUrl,
      headers = List(Authorization(OAuth2BearerToken(bearerTokenValue)))
    )
  }

  private def extractIdFrom(eventFetchUrl: String): String = eventFetchUrl split "/" last

}

class CouldNotFetchRawMarketplaceEventException extends RuntimeException
