package com.github.emildafinov.ad.sdk.http.server.routing.directives

import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import com.github.emildafinov.ad.sdk.authentication.{AppMarketCredentialsSupplier, AppMarketCredentials, OAuthAuthenticatorFactory, UnknownClientKeyException}

import scala.concurrent.ExecutionContext

/**
  * Authenticator directive used by the connector.
  * Extracts the request http method and url and attempts to authenticate the request
  * using an authenticator function from the dependent factory
  */
object ConnectorAuthenticationDirective {
  def apply(af: OAuthAuthenticatorFactory)
           (implicit ec: ExecutionContext): Directive1[AppMarketCredentials] =

    (extractMethod & extractUri) tflatMap { case (httpMethod, url) =>
      authenticateOrRejectWithChallenge(
        af.authenticatorFunction(
          requestHttpMethodName = httpMethod.value,
          requestUrl = url.toString()
        ) _
      )
    }
}
