package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.server.{Directive, Directive1, Directives}
import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.event.RoutingDependenciesModule
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule
import com.github.emildafinov.ad.sdk.payload.Event
import com.github.emildafinov.ad.sdk.server.routing.directives.{ConnectorAuthenticationDirective, SignedFetchDirective}

trait CustomDirectivesModule extends Directives {
  this: ClientDefinedDependenciesModule
    with RoutingDependenciesModule
    with AkkaDependenciesModule =>
  
  def signedFetchEvent(clientId: String): Directive[(String, Event)] =
    SignedFetchDirective(eventFetcher, clientId)

  val authenticateAppMarketRequest: Directive1[String] = 
    ConnectorAuthenticationDirective(authenticatorFactory)

  

}
