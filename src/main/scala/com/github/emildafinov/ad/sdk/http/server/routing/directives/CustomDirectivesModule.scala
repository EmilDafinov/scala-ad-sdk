package com.github.emildafinov.ad.sdk.http.server.routing.directives

import akka.http.scaladsl.server.{Directive, Directive1, Directives}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentials
import com.github.emildafinov.ad.sdk.event.RoutingDependenciesModule
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule
import com.github.emildafinov.ad.sdk.payload.Event

trait CustomDirectivesModule extends Directives {
  this: RoutingDependenciesModule
    with AkkaDependenciesModule =>
  
  def signedFetchEvent(clientCredentials: AppMarketCredentials): Directive[(String, Event)] =
    SignedFetchDirective(eventFetcher, clientCredentials)

  val authenticateAppMarketRequest: Directive1[AppMarketCredentials] = 
    ConnectorAuthenticationDirective(authenticatorFactory)
}
