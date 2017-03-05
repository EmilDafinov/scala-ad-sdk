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

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.control.NonFatal

/**
  * @param credentialsSupplier that provides the secret corresponding to the client key
  * @param authorizationTokenGenerator service used to generate the authorization token 
  *                                    needed to sign the AppMarket request    
  * @
  * @throws CouldNotFetchRawMarketplaceEventException in case the signed fethc fails
  */
class AppMarketEventFetcher(credentialsSupplier: CredentialsSupplier,
                            authorizationTokenGenerator: AuthorizationTokenGenerator,
                            appMarketTimeoutInterval: FiniteDuration = 15 seconds)
                           (implicit as: ActorSystem, am: Materializer, ec: ExecutionContext) extends EventJsonSupport{

  def fetchRawAppMarketEvent(eventFetchUrl: String, clientKey: String): Event = {
    try {
      val marketplaceCredentials = credentialsSupplier.readCredentialsFor(clientKey).get()
      val (_, headerValue) = authorizationTokenGenerator.generateAuthorizationHeader(
        "GET",
        eventFetchUrl,
        marketplaceCredentials
      )

      val eventFetchRequest = HttpRequest(
        method = GET,
        uri = eventFetchUrl,
        headers = List(Authorization(OAuth2BearerToken(headerValue)))
      )
      val eventFuture = for {
        response <- Http().singleRequest(eventFetchRequest)
        responseBody <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
        responseBodyString = responseBody.decodeString("utf8")
      } yield 
        responseBodyString
        .parseJson
        .convertTo[Event]

      Await.result(
        awaitable = eventFuture,
        atMost = appMarketTimeoutInterval
      )
    } catch {
      case NonFatal(e) => throw new CouldNotFetchRawMarketplaceEventException(e)
    }
  }
}

case class ClientCredentials(clientKey: String, clientSecret: String) extends MarketplaceCredentials

class CouldNotFetchRawMarketplaceEventException(cause: Throwable) extends Exception(cause)
