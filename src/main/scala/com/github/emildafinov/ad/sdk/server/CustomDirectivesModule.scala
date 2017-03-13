package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.server.{Directive, Directive1, Directives}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.MarketplaceCredentials
import com.github.emildafinov.ad.sdk.event.RoutingDependenciesModule
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule
import com.github.emildafinov.ad.sdk.payload.Event
import com.github.emildafinov.ad.sdk.server.routing.directives.{ConnectorAuthenticationDirective, SignedFetchDirective}

trait CustomDirectivesModule extends Directives {
  this: ClientDefinedDependenciesModule
    with RoutingDependenciesModule
    with AkkaDependenciesModule =>
  
  def signedFetchEvent(clientId: MarketplaceCredentials): Directive[(String, Event)] =
    SignedFetchDirective(eventFetcher, clientId.clientKey())

  val authenticateAppMarketRequest: Directive1[MarketplaceCredentials] = 
    ConnectorAuthenticationDirective(authenticatorFactory, credentialsSupplier)
}
