package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.{ClientDefinedCredentialsModule, ClientDefinedDependenciesModule, ConnectorResolversAkkaDependenciesModule}
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.marshallers.EventResultMarshallersModule
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse

package object resolvers {

  def getEventResolverFactory(credSupplier: AppMarketCredentialsSupplier): EventResolverFactory = {
    
    val context = new ClientDefinedCredentialsModule 
                          with ConnectorResolversAkkaDependenciesModule 
                          with EventResultMarshallersModule 
                          with EventResolversModule {
      override protected val credentialsSupplier: AppMarketCredentialsSupplier = credSupplier
    }
    new EventResolverFactory {
      override def subscriptionOrderResolver(): EventResolver[SubscriptionOrderResponse] = context.subscriptionOrderEventResolver
    }
  }
}
