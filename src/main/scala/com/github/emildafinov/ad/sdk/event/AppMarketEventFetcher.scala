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
import spray.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.control.NonFatal

/**
  * @param credentialsSupplier         that provides the secret corresponding to the client key
  * @param authorizationTokenGenerator service used to generate the authorization token 
  *                                    needed to sign the AppMarket request    
  *                                    @
  * @throws CouldNotFetchRawMarketplaceEventException in case the signed fethc fails
  */
class AppMarketEventFetcher(credentialsSupplier: CredentialsSupplier,
                            authorizationTokenGenerator: AuthorizationTokenGenerator)
                           (implicit as: ActorSystem,
                            am: Materializer,
                            ec: ExecutionContext) extends EventJsonSupport {

  def fetchRawAppMarketEvent(eventFetchUrl: String, clientKey: String): Future[Event] = {
    
    val eventFuture = for {
      eventFetchRequest <- signedFetchRequest(eventFetchUrl, clientKey)
      response <- Http().singleRequest(eventFetchRequest)
      responseBody <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
      responseBodyString = responseBody.decodeString("utf8")
    } yield responseBodyString
        .parseJson
        .convertTo[Event]

    eventFuture recover {
      case NonFatal(_) => throw new CouldNotFetchRawMarketplaceEventException()
    }
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
  
}

case class ClientCredentials(clientKey: String, clientSecret: String) extends MarketplaceCredentials

class CouldNotFetchRawMarketplaceEventException extends RuntimeException
