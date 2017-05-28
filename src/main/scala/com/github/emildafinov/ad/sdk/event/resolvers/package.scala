package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.marshallers.EventResultMarshallersModule
import com.github.emildafinov.ad.sdk.{ClientDefinedCredentialsModule, ConnectorResolversAkkaDependenciesModule}

package object resolvers {

  def getEventResolverFactory(credSupplier: AppMarketCredentialsSupplier): EventResolverFactory = {
    
    val context = new ClientDefinedCredentialsModule 
                          with ConnectorResolversAkkaDependenciesModule 
                          with EventResultMarshallersModule 
                          with EventResolversModule {
      override protected val credentialsSupplier: AppMarketCredentialsSupplier = credSupplier
    }
    () => context.subscriptionOrderEventResolver
  }
}
