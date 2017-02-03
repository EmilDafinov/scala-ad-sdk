package com.emiliorodo.ad.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.configuration.ApplicationConfigurationModule


trait RoutesModule extends Directives {
  this: ApplicationConfigurationModule with HealthRoutes =>

  lazy val baseRoute: Route =
    sample

}



