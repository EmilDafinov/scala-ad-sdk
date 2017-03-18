package com.github.emildafinov.ad.sdk.http.server.routes

import akka.http.scaladsl.server.{Directives, Route}


private[sdk] trait RoutesModule extends Directives {

  this: AppMarketCommunicationRoutesModule =>

  /**
    * This is the base route of the application, the one that is passed to the HTTP server
    */
  lazy val baseRoute: Route = appMarketIntegrationRoutes
}



