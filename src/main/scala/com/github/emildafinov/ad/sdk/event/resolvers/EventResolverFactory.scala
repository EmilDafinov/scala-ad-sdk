package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.{ClientDefinedCredentialsModule, ConnectorResolversAkkaDependenciesModule}
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.EventResolverFactory
import com.github.emildafinov.ad.sdk.event.marshallers.EventResultMarshallersModule
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse

object EventResolverFactory {
  def apply(credSupplier: AppMarketCredentialsSupplier): EventResolverFactory = {
    val factoryApplicationContext = new ClientDefinedCredentialsModule
      with ConnectorResolversAkkaDependenciesModule
      with EventResultMarshallersModule
      with EventResolversModule {

      override protected val credentialsSupplier: AppMarketCredentialsSupplier = credSupplier
    }

    new EventResolverFactory {
      override def subscriptionOrderResolver(): EventResolver[SubscriptionOrderResponse] = factoryApplicationContext.subscriptionOrderEventResolver
    }
  }
}

