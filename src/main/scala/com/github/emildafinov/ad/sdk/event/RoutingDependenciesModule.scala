package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.{AuthorizationTokenGenerator, OAuthAuthenticatorFactory, OauthSignatureParser}
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventFetcher
import com.github.emildafinov.ad.sdk.internal.{ClientDefinedCredentialsModule, ClientDefinedDependenciesModule}

trait RoutingDependenciesModule {
  this: ClientDefinedDependenciesModule
    with ClientDefinedCredentialsModule
    with AkkaDependenciesModule =>

  lazy val authorizationTokenGenerator = new AuthorizationTokenGenerator()

  lazy implicit val eventFetcher = new AppMarketEventFetcher(
    authorizationTokenGenerator = authorizationTokenGenerator
  )

  lazy val oauthSignatureParser = new OauthSignatureParser()

  lazy val authenticatorFactory = new OAuthAuthenticatorFactory(
    credentialsSupplier,
    authorizationTokenGenerator,
    oauthSignatureParser
  )
}
