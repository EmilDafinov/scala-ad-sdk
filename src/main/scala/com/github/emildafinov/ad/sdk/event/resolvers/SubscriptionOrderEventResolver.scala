package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentialsSupplier, AuthorizationTokenGenerator}
import com.github.emildafinov.ad.sdk.event.SdkProvidedEventResolver
import com.github.emildafinov.ad.sdk.event.marshallers.SubscriptionOrderResponseMarshaller
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver

object SubscriptionOrderEventResolver {

  def build(credentialsSupplier: AppMarketCredentialsSupplier): EventResolver[SubscriptionOrderResponse] = {
    val authorizationTokenGenerator = new AuthorizationTokenGenerator
    val eventResolver = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)
    new SdkProvidedEventResolver(eventResolver, SubscriptionOrderResponseMarshaller())
  }
}
