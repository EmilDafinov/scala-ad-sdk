package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.authentication.AuthorizationTokenGenerator
import com.github.emildafinov.ad.sdk.event.SdkProvidedEventResolver
import com.github.emildafinov.ad.sdk.event.marshallers.EventResultMarshallersModule
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.{AkkaDependenciesModule, ClientDefinedCredentialsModule}

trait EventResolversModule {
  this: AkkaDependenciesModule 
    with ClientDefinedCredentialsModule 
    with EventResultMarshallersModule =>

  lazy val authorizationTokenGenerator = new AuthorizationTokenGenerator
  lazy val baseEventResolver = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)
  lazy val subscriptionOrderEventResolver = new SdkProvidedEventResolver(baseEventResolver, subscriptionOrderResponseMarshaller)
}
