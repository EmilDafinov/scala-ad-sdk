package com.emiliorodo.ad.configuration

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Module that resolves and exposes the [[com.typesafe.config.Config]] instance containing
  * the application configuration
  * @author edafinov
  */
trait ApplicationConfigurationModule 
  extends WebappConfig 
     with HttpServerConfig {
  
  lazy val config = ConfigFactory.load()
  
  val webappConfig = config.getConfig("webapp")
   
}

trait WebappConfig {
  val webappConfig: Config
}

trait HttpServerConfig {
  abstract val httpServerConfig: Config
}
