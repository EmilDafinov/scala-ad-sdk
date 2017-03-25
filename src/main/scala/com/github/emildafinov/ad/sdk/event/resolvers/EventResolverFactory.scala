package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.internal.ClientDefinedCredentialsModule

class EventResolverFactory(cs: AppMarketCredentialsSupplier) 
  extends AkkaDependenciesModule with ClientDefinedCredentialsModule {

  override val credentialsSupplier: AppMarketCredentialsSupplier = cs

}
