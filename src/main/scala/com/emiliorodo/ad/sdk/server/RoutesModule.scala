package com.emiliorodo.ad.sdk.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.sdk.configuration.ApplicationConfigurationModule

private[sdk] trait RoutesModule extends Directives {
  this: ApplicationConfigurationModule with HealthRoutes =>

  lazy val baseRoute: Route =
    sample

}



