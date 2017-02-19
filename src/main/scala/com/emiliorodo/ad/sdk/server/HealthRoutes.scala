package com.emiliorodo.ad.sdk.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.AkkaDependenciesModule
import com.emiliorodo.ad.sdk.service.DummyServiceModule
import io.github.lhotari.akka.http.health.HealthEndpoint.createDefaultHealthRoute

/**
  * @author edafinov
  */
private[sdk]  trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule with DummyServiceModule =>
  lazy val health:Route = createDefaultHealthRoute()
}
