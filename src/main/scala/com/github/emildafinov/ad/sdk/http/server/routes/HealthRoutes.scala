package com.github.emildafinov.ad.sdk.http.server.routes

import akka.http.scaladsl.server.{Directives, Route}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import io.github.lhotari.akka.http.health.HealthEndpoint.createDefaultHealthRoute

/**
  * Defines the health route used by the HTTP server
  */
private[sdk] trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule =>
  lazy val health: Route = createDefaultHealthRoute()
}
