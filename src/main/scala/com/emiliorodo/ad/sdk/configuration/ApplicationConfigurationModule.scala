package com.emiliorodo.ad.sdk.configuration

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Module that resolves and exposes the [[com.typesafe.config.Config]] instance containing
  * the application configuration
  * @author edafinov
  */
private[sdk] trait ApplicationConfigurationModule {
  lazy val config: Config = ConfigFactory.load()
}


