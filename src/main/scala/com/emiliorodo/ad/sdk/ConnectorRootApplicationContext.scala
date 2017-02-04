package com.emiliorodo.ad.sdk

import com.emiliorodo.ad.sdk.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.sdk.server.{HealthRoutes, RoutesModule}
import com.emiliorodo.ad.sdk.service.DummyServiceModule

trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RoutesModule
