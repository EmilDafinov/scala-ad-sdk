package com.emiliorodo.ad.server

import akka.http.scaladsl.server.{Directives, Route}
import com.emiliorodo.ad.configuration.WebappConfig


trait RoutesModule extends Directives {
  this: WebappConfig with HealthRoutes =>
  
  private lazy val webAppRootDirectory = webappConfig.getString("root.directory")
  private lazy val staticContent: Route = getFromResourceDirectory(webAppRootDirectory)
  
  lazy val baseRoute: Route =
    
    staticContent ~
    sample ~
    ping
}



