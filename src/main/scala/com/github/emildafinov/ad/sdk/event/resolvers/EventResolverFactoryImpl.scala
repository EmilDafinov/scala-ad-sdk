package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.ConnectorResolversAkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.internal.ClientDefinedCredentialsModule

class EventResolverFactoryImpl(cs: AppMarketCredentialsSupplier) 
  extends ConnectorResolversAkkaDependenciesModule 
    with EventResolversModule
    with ClientDefinedCredentialsModule {
  override val credentialsSupplier: AppMarketCredentialsSupplier = cs
}
