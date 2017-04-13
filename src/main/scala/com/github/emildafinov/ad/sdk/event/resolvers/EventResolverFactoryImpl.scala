package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.{ClientDefinedCredentialsModule, ConnectorResolversAkkaDependenciesModule}
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier

class EventResolverFactoryImpl(cs: AppMarketCredentialsSupplier) 
  extends ConnectorResolversAkkaDependenciesModule 
    with EventResolversModule
    with ClientDefinedCredentialsModule {
  override val credentialsSupplier: AppMarketCredentialsSupplier = cs
}
