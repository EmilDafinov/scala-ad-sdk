package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.BadRequest
import akka.http.scaladsl.server.{Directives, ExceptionHandler, Route}
import com.github.emildafinov.ad.sdk.payload.EventJsonSupport


private[sdk] trait RoutesModule extends Directives with EventJsonSupport {

  this: AppMarketCommunicationRoutesModule with HealthRoutes =>

  lazy val baseRoute: Route =
    handleExceptions(rootExceptionHandler) {
      health ~ appMarketIntegrationRoutes
    }

  lazy val rootExceptionHandler = ExceptionHandler {
      case _: ArithmeticException =>
        complete(HttpResponse(BadRequest, entity = "The operation you requested is not supported"))
    }
}



