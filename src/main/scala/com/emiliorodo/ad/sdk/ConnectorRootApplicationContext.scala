package com.emiliorodo.ad.sdk

import com.emiliorodo.ad.sdk.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.sdk.internal.ClientDefinedEventHandlersModule
import com.emiliorodo.ad.sdk.server.{HealthRoutes, RichEventParsersModule, RoutesModule}
import com.emiliorodo.ad.sdk.service.DummyServiceModule

private[sdk] trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RichEventParsersModule
  with RoutesModule {

  this: ClientDefinedEventHandlersModule =>
}
