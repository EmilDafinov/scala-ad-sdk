package com.emiliorodo.ad.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.configuration.ApplicationConfigurationModule


trait RoutesModule extends Directives {
  this: ApplicationConfigurationModule with HealthRoutes =>
  
  private lazy val webAppRootDirectory = webappConfig.getString("root.directory")
  private lazy val staticContent: Route = getFromResourceDirectory(webAppRootDirectory)
  
  lazy val baseRoute: Route =
    
    staticContent ~
    sample
  
  val abc: Route = baseRoute
}



