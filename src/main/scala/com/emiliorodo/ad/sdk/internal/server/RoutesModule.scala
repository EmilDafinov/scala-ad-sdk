package com.emiliorodo.ad.sdk.internal.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.internal.configuration.ApplicationConfigurationModule


trait RoutesModule extends Directives {
  this: ApplicationConfigurationModule with HealthRoutes =>

  lazy val baseRoute: Route =
    sample

}



