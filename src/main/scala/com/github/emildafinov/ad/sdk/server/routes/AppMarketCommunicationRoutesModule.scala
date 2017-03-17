package com.github.emildafinov.ad.sdk.server.routes

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, ExceptionHandler, Route}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.event.{CouldNotFetchRawMarketplaceEventException, MalformedRawMarketplaceEventPayloadException}
import com.github.emildafinov.ad.sdk.payload.{ApiResults, Event}
import com.github.emildafinov.ad.sdk.server.RawEventHandlersModule
import com.github.emildafinov.ad.sdk.server.routing.directives.CustomDirectivesModule
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

import scala.language.postfixOps

/**
  * Describes the SDK-defined routes that handle communication with the AppMarket
  */
private[sdk] trait AppMarketCommunicationRoutesModule extends Directives with Json4sSupport {

  this: RawEventHandlersModule
    with CustomDirectivesModule
    with AkkaDependenciesModule =>

  private implicit val formats = Serialization.formats(NoTypeHints)

  def appMarketIntegrationRoutes: Route =
    authenticateAppMarketRequest { clientCredentials =>
      pathPrefix("integration") {
        handleExceptions(integrationExceptionHandler) {
          signedFetchEvent(clientCredentials) { case (eventId, rawMarketplaceEvent) =>

            subscriptionOrder(eventId, rawMarketplaceEvent, clientCredentials.clientKey()) ~
            subscriptionCancel(eventId, rawMarketplaceEvent, clientCredentials.clientKey()) ~
            subscriptionChange(eventId, rawMarketplaceEvent, clientCredentials.clientKey()) ~
            subscriptionNotice(eventId, rawMarketplaceEvent, clientCredentials.clientKey()) ~
            userAssignment(eventId, rawMarketplaceEvent, clientCredentials.clientKey()) ~
            userUnassignment(eventId, rawMarketplaceEvent, clientCredentials.clientKey())
          }
        }
      }
    }

  private def isForAddon(event: Event) =
    event.payload.account.flatMap(_.parentAccountIdentifier) isDefined

  def subscriptionOrder(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "order") {
      complete {
        val clientEventHandler =
          if (isForAddon(event))
            addonSubscriptionOrderRawEventHandler
          else
            subscriptionOrderRawEventHandler

        clientEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def subscriptionCancel(eventId: String, event: Event, callerCredentials: String): Route =
    path("subscription" / "cancel") {
      complete {

        val clientEventHandler =
          if (isForAddon(event))
            addonSubscriptionCancelRawEventHandler
          else
            subscriptionCancelRawEventHandler

        clientEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = callerCredentials
        )
      }
    }

  def subscriptionChange(eventId: String, event: Event, callerCredentials: String): Route =
    path("subscription" / "change") {
      complete {
        subscriptionChangedRawEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = callerCredentials
        )
      }
    }

  def subscriptionNotice(eventId: String, event: Event, callerCredentials: String): Route =
    path("subscription" / "notice") {
      complete {
        val clientEventHandler = event.payload.notice.map(_.`type`) match {
          case Some("CLOSED") => subscriptionClosedRawEventHandler
          case Some("DEACTIVATED") => subscriptionDeactivatedRawEventHandler
          case Some("REACTIVATED") => subscriptionReactivatedRawEventHandler
          case Some("UPCOMING_INVOICE") => subscriptionUpcomingInvoiceRawEventHandler
          case _ => throw new MalformedRawMarketplaceEventPayloadException(null)
        }
        clientEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = callerCredentials
        )
      }
    }

  def userAssignment(eventId: String, event: Event, callerCredentials: String): Route =
    path("user" / "assignment") {
      complete {
        userAssignmentRawEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = callerCredentials
        )
      }
    }

  def userUnassignment(eventId: String, event: Event, callerCredentials: String): Route =
    path("user" / "unassignment") {
      complete {
        userUnassignmentRawEventHandler.processRawEvent(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = callerCredentials
        )
      }
    }

  lazy val integrationExceptionHandler = ExceptionHandler {
    case _: CouldNotFetchRawMarketplaceEventException =>
      complete {
        HttpResponse(
          StatusCodes.InternalServerError,
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string = write(ApiResults.failure("Could not perform signed fetch"))
          )
        )
      }

    case _: MalformedRawMarketplaceEventPayloadException =>
      complete {
        HttpResponse(
          StatusCodes.BadRequest,
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string = write(ApiResults.failure("The event payload was malformed"))
          )
        )
      }
  }
}


