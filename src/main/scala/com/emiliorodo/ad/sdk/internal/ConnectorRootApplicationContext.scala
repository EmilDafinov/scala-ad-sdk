package com.emiliorodo.ad.sdk.internal

import com.emiliorodo.ad.sdk.internal.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.sdk.internal.server.{HealthRoutes, RoutesModule}
import com.emiliorodo.ad.sdk.internal.service.DummyServiceModule

trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RoutesModule
