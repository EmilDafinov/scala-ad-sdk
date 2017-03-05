package com.github.emildafinov.ad.sdk

import com.github.emildafinov.ad.sdk.configuration.ApplicationConfigurationModule
import com.github.emildafinov.ad.sdk.event.RawEventHandlersModule
import com.github.emildafinov.ad.sdk.event.parsers.RichEventParsersModule
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule
import com.github.emildafinov.ad.sdk.server.{AppMarketCommunicationRoutesModule, HealthRoutes, RoutesModule}
import com.github.emildafinov.ad.sdk.service.DummyServiceModule

private[sdk] trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with RichEventParsersModule
  with HealthRoutes
  with RawEventHandlersModule
  with AppMarketCommunicationRoutesModule
  with RoutesModule {

  this: ClientDefinedDependenciesModule =>
}
