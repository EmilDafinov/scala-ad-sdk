package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.{AkkaDependenciesModule, ClientDefinedCredentialsModule}
import com.github.emildafinov.ad.sdk.authentication.AuthorizationTokenGenerator
import com.github.emildafinov.ad.sdk.event.SdkProvidedEventResolver
import com.github.emildafinov.ad.sdk.event.marshallers.SubscriptionOrderResponseMarshaller
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver

trait EventResolversModule {
  this: AkkaDependenciesModule with ClientDefinedCredentialsModule =>

  lazy val authorizationTokenGenerator = new AuthorizationTokenGenerator
  lazy val eventResolver = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)
  lazy val subscriptionOrderEventResolver = new SdkProvidedEventResolver(eventResolver, SubscriptionOrderResponseMarshaller())
}
