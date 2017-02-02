package com.emiliorodo.ad.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.AkkaDependenciesModule
import com.emiliorodo.ad.service.DummyServiceModule

/**
  * @author edafinov
  */
trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule with DummyServiceModule =>

  lazy val sample: Route =
    (path("sample") & get) {
      complete {
        dummyService.getAkkaWebPageAsString
      }
    }

}

case class PingResponse(message: String = "Pong!")
