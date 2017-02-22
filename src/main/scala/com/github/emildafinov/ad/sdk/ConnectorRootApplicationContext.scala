package com.github.emildafinov.ad.sdk

import com.github.emildafinov.ad.sdk.configuration.ApplicationConfigurationModule
import com.github.emildafinov.ad.sdk.events.parsers.RichEventParsersModule
import com.github.emildafinov.ad.sdk.internal.ClientDefinedEventHandlersModule
import com.github.emildafinov.ad.sdk.server.{HealthRoutes, RoutesModule}
import com.github.emildafinov.ad.sdk.service.DummyServiceModule

private[sdk] trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RichEventParsersModule
  with RoutesModule {

  this: ClientDefinedEventHandlersModule =>
}
