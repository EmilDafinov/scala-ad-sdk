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
            subscriptionOrder(eventId, rawMarketplaceEvent, clientId)
            //        ~ subscriptionCancel ~ subscriptionChange ~ subscriptionNotice ~
            //          addonOrder ~ addonCancel ~
            //          userAssignment ~ userUnassignment
          }
        }
      }
    }

  def subscriptionOrder(eventId: String, event: Event, clientId: String): Route =
    path("subscription" / "order") {
      complete {
        subscriptionOrderRawEventHandler.processEventFrom(
          rawEvent = event,
          rawEventId = eventId,
          clientKey = clientId
        )
      }
    }

  def subscriptionCancel(implicit eventCoordinates: EventCoordinates): Route =
    path("subscription" / "cancel") {
      complete(???)
    }

  def subscriptionChange(implicit eventCoordinates: EventCoordinates): Route =
    path("subscription" / "change") {
      complete(???)
    }

  def subscriptionNotice(implicit eventCoordinates: EventCoordinates): Route =
    path("subscription" / "notice") {
      complete(???)
    }

  def addonOrder(implicit eventCoordinates: EventCoordinates): Route =
    path("subscription" / "addon" / "order") {
      complete(???)
    }

  def addonCancel(implicit eventCoordinates: EventCoordinates): Route =
    path("subscription" / "addon" / "cancel") {
      complete(???)
    }

  def userAssignment(implicit eventCoordinates: EventCoordinates): Route =
    path("user" / "assignment") {
      complete(???)
    }

  def userUnassignment(implicit eventCoordinates: EventCoordinates): Route =
    path("user" / "unassignment") {
      complete(???)
    }
}


