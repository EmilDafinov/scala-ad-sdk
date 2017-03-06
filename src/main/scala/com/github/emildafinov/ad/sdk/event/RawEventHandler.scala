package com.github.emildafinov.ad.sdk.event

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.Accepted
import com.github.emildafinov.ad.sdk.EventHandler
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults, Event}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.Success

/**
  * Asynchronously handles a raw marketplace event.
  *
  * @param transformToClientEvent a function that takes a raw [[Event]] and a string representing the eventId and p
  *                               produces the event instance visible to the client handler
  * @param clientEventHandler     the logic that is being executed from the client connector upon receival of the event]
  * @param toMarketplaceResponse  function that marshals the result of the client event handling into an [ApiResult
  * @tparam A Rich event type
  * @tparam B Result of processing the rich event
  */
class RawEventHandler[A, B](transformToClientEvent: (Event, String) => A,
                            clientEventHandler: EventHandler[A, B],
                            toMarketplaceResponse: B => ApiResult)
                           (implicit appMarketEventResolver: AppMarketEventResolver, appMarketEventFetcher: AppMarketEventFetcher) {
  
  def extractIdFrom(eventFetchUrl: String): String = eventFetchUrl split "/" last

  def processEventFrom(eventFetchUrl: String, clientKey: String, appMarketTimeoutInterval: FiniteDuration = 15 seconds)
                      (implicit ec: ExecutionContext): Future[HttpResponse] = Future {
    
    val rawEvent = Await.result(
      awaitable = appMarketEventFetcher.fetchRawAppMarketEvent(eventFetchUrl, clientKey),
      atMost = appMarketTimeoutInterval
    )

    val rawEventId = extractIdFrom(eventFetchUrl)
    val richEvent = transformToClientEvent(rawEvent, rawEventId)

    Future {
      clientEventHandler.handle(richEvent)
    } onComplete {
      case Success(eventProcessingResult) =>
        appMarketEventResolver.sendEventResolvedCallback(
          resolveEndpointBaseUrl = rawEvent.marketplace.baseUrl,
          eventId = rawEventId,
          eventProcessingResult = toMarketplaceResponse(eventProcessingResult)
        )
      case _ =>
        appMarketEventResolver.sendEventResolvedCallback(
          resolveEndpointBaseUrl = rawEvent.marketplace.baseUrl,
          eventId = rawEventId,
          eventProcessingResult = ApiResults.failure("An unknown error has occurred")
        )
    }
    HttpResponse(status = Accepted)
  }
}

class MalformedRawMarketplaceEventPayloadException(cause: Throwable) extends Exception(cause)
