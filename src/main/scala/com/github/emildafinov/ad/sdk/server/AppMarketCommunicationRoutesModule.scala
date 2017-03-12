package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, ExceptionHandler, Route}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.event.{CouldNotFetchRawMarketplaceEventException, MalformedRawMarketplaceEventPayloadException, RawEventHandlersModule, RoutingDependenciesModule}
import com.github.emildafinov.ad.sdk.payload.{ApiResults, Event, EventJsonSupport}
import spray.json._

import scala.language.postfixOps

/**
  * Describes the SDK-defined routes that handle communication with the AppMarket
  */
private[sdk] trait AppMarketCommunicationRoutesModule extends Directives with EventJsonSupport {

  this: RawEventHandlersModule
    with EventResultMarshallersModule
    with CustomDirectivesModule
    with AkkaDependenciesModule
    with RoutingDependenciesModule =>

  lazy val integrationExceptionHandler = ExceptionHandler {
    case _: CouldNotFetchRawMarketplaceEventException =>
      complete {
        HttpResponse(
          StatusCodes.InternalServerError,
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string = ApiResults.failure("Could not perform signed fetch").toJson.prettyPrint
          )
        )
      }

    case _: MalformedRawMarketplaceEventPayloadException =>
      complete {
        HttpResponse(
          StatusCodes.BadRequest,
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string = ApiResults.failure("The event payload was malformed").toJson.prettyPrint
          )
        )
      }
  }

  def appMarketIntegrationRoutes: Route =
    authenticateAppMarketRequest { clientId =>
      pathPrefix("integration") {
        handleExceptions(integrationExceptionHandler) {
          signedFetchEvent(clientId) { case (eventId, rawMarketplaceEvent) =>
            subscriptionOrder(eventId, rawMarketplaceEvent, clientId) ~
              subscriptionCancel(eventId, rawMarketplaceEvent, clientId) ~
              subscriptionChange(eventId, rawMarketplaceEvent, clientId) ~
              subscriptionNotice(eventId, rawMarketplaceEvent, clientId) ~
              userAssignment(eventId, rawMarketplaceEvent, clientId) ~
              userUnassignment(eventId, rawMarketplaceEvent, clientId)
          }
        }
      }
    }

  private def isForAddon(event: Event) = //event.payload.flatMap(_.account).map(_.parentAccountIdentifier)) isDefined
    event.payload.account.flatMap(_.parentAccountIdentifier) isDefined

  def subscriptionOrder(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "order") {
      complete {
        val clientEventHandler = 
          if (isForAddon(event)) 
            addonSubscriptionOrderRawEventHandler
          else 
            subscriptionOrderRawEventHandler
        
        clientEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def subscriptionCancel(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "cancel") {
      complete {

        val clientEventHandler =
          if (isForAddon(event)) 
            addonSubscriptionCancelRawEventHandler
          else 
            subscriptionCancelRawEventHandler

        clientEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def subscriptionChange(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "change") {
      complete {
        subscriptionChangedRawEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def subscriptionNotice(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "notice") {
      complete {
        val clientEventHandler = event.payload.notice.map(_.`type`) match {
          case Some("CLOSED") => subscriptionClosedRawEventHandler
          case Some("DEACTIVATED") => subscriptionDeactivatedRawEventHandler
          case Some("REACTIVATED") => subscriptionReactivatedRawEventHandler
          case Some("UPCOMING_INVOICE") => subscriptionUpcomingInvoiceRawEventHandler
          case _ => throw new MalformedRawMarketplaceEventPayloadException(null)
        }
        clientEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def userAssignment(eventId: String, event: Event, clientId: String): Route =
    path("user" / "assignment") {
      complete {
        userAssignmentRawEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def userUnassignment(eventId: String, event: Event, clientId: String): Route =
    path("user" / "unassignment") {
      complete {
        userUnassignmentRawEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }
}


