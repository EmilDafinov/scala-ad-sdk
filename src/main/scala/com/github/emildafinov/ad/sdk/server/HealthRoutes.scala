package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.server.{Directives, Route}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.service.DummyServiceModule
import io.github.lhotari.akka.http.health.HealthEndpoint.createDefaultHealthRoute

/**
  * Defines the health
  */
private[sdk]  trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule with DummyServiceModule =>
  lazy val health: Route = createDefaultHealthRoute()
}
