package com.emiliorodo.ad.sdk.server

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.AkkaDependenciesModule
import com.emiliorodo.ad.sdk.service.DummyServiceModule
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * @author edafinov
  */
private[sdk]  trait HealthRoutes extends Directives {
  this: AkkaDependenciesModule with DummyServiceModule =>
  import PongJsonSupport._
  lazy val sample: Route =
    path("sample") {
      complete {
        dummyService.getAkkaWebPageAsString
      }
    } ~
    path("ping") {
      complete(Pong())
    }
}

case class Pong(message: String = "Pong")
object PongJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val eventFormat: RootJsonFormat[Pong] = jsonFormat1(Pong)
}
