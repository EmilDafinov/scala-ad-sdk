package com.emiliorodo.ad

import com.emiliorodo.ad.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.server.{HealthRoutes, RoutesModule}
import com.emiliorodo.ad.service.DummyServiceModule

/**
  * @author edafinov
  */
trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RoutesModule 

