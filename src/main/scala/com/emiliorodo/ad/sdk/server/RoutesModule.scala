package com.emiliorodo.ad.sdk.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.AkkaDependenciesModule
import com.emiliorodo.ad.sdk.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.sdk.internal.ClientDefinedEventHandlersModule
import com.emiliorodo.ad.sdk.payload.Event

import scala.concurrent.Future

private[sdk] trait RoutesModule extends Directives {
  this: ApplicationConfigurationModule with HealthRoutes with ClientDefinedEventHandlersModule with RichEventParsersModule with AkkaDependenciesModule =>

  import com.emiliorodo.ad.sdk.payload.EventJsonSupport._

  lazy val baseRoute: Route =
    sample ~ appmarketIntegrationRoutes

  def appmarketIntegrationRoutes: Route =
    (pathPrefix("integration") & entity(as[Event])) { implicit event =>
      subscriptionOrder ~ subscriptionCancel ~ subscriptionChange ~ subscriptionNotice ~
        addonOrder ~ addonCancel ~
        userAssignment ~ userUnassignment
    }

  def subscriptionOrder(implicit event: Event): Route =
    path("subscription" / "order") {
      complete {
        Future {
          subscriptionOrderHandler.handle(subscriptionOrderEventParser(event))
        }
      }
    }

  def subscriptionCancel(implicit event: Event): Route =
    path("subscription" / "cancel") {
      complete(???)
    }

  def subscriptionChange(implicit event: Event): Route =
    path("subscription" / "change") {
      complete(???)
    }

  def subscriptionNotice(implicit event: Event): Route =
    path("subscription" / "notice") {
      complete(???)
    }

  def addonOrder(implicit event: Event): Route =
    path("subscription" / "addon" / "order") {
      complete(???)
    }

  def addonCancel(implicit event: Event): Route =
    path("subscription" / "addon" / "cancel") {
      complete(???)
    }

  def userAssignment(implicit event: Event): Route =
    path("user" / "assignment") {
      complete(???)
    }

  def userUnassignment(implicit event: Event): Route =
    path("user" / "unassignment") {
      complete(???)
    }
}



