package com.github.emildafinov.ad.sdk.event.resolvers

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentialsSupplier, AuthorizationTokenGenerator}
import com.github.emildafinov.ad.sdk.event.SdkProvidedEventResolver
import com.github.emildafinov.ad.sdk.event.marshallers.SubscriptionOrderResponseMarshaller
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver

import scala.concurrent.ExecutionContext

object SubscriptionOrderEventResolver {

  def apply(credentialsSupplier: AppMarketCredentialsSupplier)
           (implicit
             ec: ExecutionContext,
             as: ActorSystem,
             ma: Materializer): EventResolver[SubscriptionOrderResponse] = {

    val authorizationTokenGenerator = new AuthorizationTokenGenerator
    val eventResolver = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)
    new SdkProvidedEventResolver(eventResolver, SubscriptionOrderResponseMarshaller())
  }
}


