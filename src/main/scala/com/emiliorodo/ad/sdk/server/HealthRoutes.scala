package com.emiliorodo.ad.sdk.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.AkkaDependenciesModule
import com.emiliorodo.ad.sdk.service.DummyServiceModule

/**
  * @author edafinov
  */
private[sdk]  trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule with DummyServiceModule =>

  lazy val sample: Route =
    path("sample") {
      complete {
        dummyService.getAkkaWebPageAsString
      }
    }
}
