package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, OAuthAuthenticatorFactory, OauthSignatureParser}
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule

trait RoutingDependenciesModule {
  this: ClientDefinedDependenciesModule 
    with AkkaDependenciesModule =>
  
  lazy val authorizationTokenGenerator = new AuthorizationTokenGenerator()
  lazy implicit val eventFetcher = new AppMarketEventFetcher(
    credentialsSupplier = credentialsSupplier,
    authorizationTokenGenerator = authorizationTokenGenerator
  )
  lazy val oauthSignatureParser = new OauthSignatureParser()
  lazy val authenticatorFactory = new OAuthAuthenticatorFactory(
    credentialsSupplier, 
    authorizationTokenGenerator, 
    oauthSignatureParser
  )
  
}
