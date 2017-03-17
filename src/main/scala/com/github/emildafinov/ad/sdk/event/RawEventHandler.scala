package com.github.emildafinov.ad.sdk.event

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.Accepted
import com.github.emildafinov.ad.sdk.{EventHandler, EventReturnAddressImpl}
import com.github.emildafinov.ad.sdk.authentication.MarketplaceCredentials
import com.github.emildafinov.ad.sdk.payload.{ApiResult, Event}

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

/**
  * Asynchronously handles a raw marketplace event of a particular type.
  *
  * @param transformToClientEvent a function that takes a raw [[Event]] and a string representing the eventId and p
  *                               produces the event instance visible to the client handler
  * @param clientEventHandler     the logic that is being executed from the client connector upon receival of the event]
  * @tparam A Rich event type
  */
class RawEventHandler[A, B](transformToClientEvent: (Event, String) => A,
                            clientEventHandler: EventHandler[A]) {
  
  def processRawEvent(rawEventId: String, rawEvent: Event, clientKey: String)
                     (implicit ec: ExecutionContext): Future[HttpResponse] = Future {

    Future {
      clientEventHandler.handle(
        transformToClientEvent(rawEvent, rawEventId),
        new EventReturnAddressImpl(
          rawEventId,
          rawEvent.marketplace.baseUrl,
          clientKey
        )
      )
    }

    HttpResponse(status = Accepted)
  }
}


