package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, ExceptionHandler, Route}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.event.{CouldNotFetchRawMarketplaceEventException, MalformedRawMarketplaceEventPayloadException, RawEventHandlersModule}
import com.github.emildafinov.ad.sdk.payload.{ApiResults, EventJsonSupport}
import spray.json._

/**
  * Describes the SDK-defined routes that handle communication with the AppMarket
  */
private[sdk] trait AppMarketCommunicationRoutesModule extends Directives with EventJsonSupport with EventResultMarshallersModule {
  this: RawEventHandlersModule
    with AkkaDependenciesModule =>


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
    handleExceptions(integrationExceptionHandler) {
      (pathPrefix("integration") & parameter("eventUrl")) { implicit eventFetchUrl =>
        subscriptionOrder ~ subscriptionCancel ~ subscriptionChange ~ subscriptionNotice ~
          addonOrder ~ addonCancel ~
          userAssignment ~ userUnassignment
      }
    }

  def subscriptionOrder(implicit eventFetchUrl: String): Route =
    path("subscription" / "order") {
      complete {
        val dummyKey = "sdfsd"
        subscriptionOrderRawEventHandler.processEventFrom(eventFetchUrl, dummyKey)
      }
    }

  def subscriptionCancel(implicit eventFetchUrl: String): Route =
    path("subscription" / "cancel") {
      complete(???)
    }

  def subscriptionChange(implicit eventFetchUrl: String): Route =
    path("subscription" / "change") {
      complete(???)
    }

  def subscriptionNotice(implicit eventFetchUrl: String): Route =
    path("subscription" / "notice") {
      complete(???)
    }

  def addonOrder(implicit eventFetchUrl: String): Route =
    path("subscription" / "addon" / "order") {
      complete(???)
    }

  def addonCancel(implicit eventFetchUrl: String): Route =
    path("subscription" / "addon" / "cancel") {
      complete(???)
    }

  def userAssignment(implicit eventFetchUrl: String): Route =
    path("user" / "assignment") {
      complete(???)
    }

  def userUnassignment(implicit eventFetchUrl: String): Route =
    path("user" / "unassignment") {
      complete(???)
    }
}


