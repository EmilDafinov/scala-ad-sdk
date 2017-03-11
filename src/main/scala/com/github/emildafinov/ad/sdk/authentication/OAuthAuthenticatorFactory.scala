package com.github.emildafinov.ad.sdk.authentication

import akka.http.scaladsl.model.headers.{HttpChallenge, HttpCredentials}
import akka.http.scaladsl.server.Directives

import scala.concurrent.{ExecutionContext, Future}
import Directives._
class OAuthAuthenticatorFactory(credentialsSupplier: CredentialsSupplier,
                                authorizationTokenGenerator: AuthorizationTokenGenerator,
                                oauthSignatureParser: OauthSignatureParser) {

  def authenticatorFunction(requestHttpMethodName: String, requestUrl: String)
                           (credentialsInRequest: Option[HttpCredentials])
                           (implicit ex: ExecutionContext): Future[AuthenticationResult[String]] =
    Future {
      credentialsInRequest match {
        case Some(callerCredentials) =>

          val requestClientKey = callerCredentials.params("oauth_consumer_key")
          val connectorCredentials = credentialsSupplier.readCredentialsFor(requestClientKey)
          val expectedOAuthToken = authorizationTokenGenerator.generateAuthorizationHeader(
            httpMethodName = requestHttpMethodName,
            resourceUrl = requestUrl,
            timeStamp = callerCredentials.params("oauth_timestamp"),
            nonce = callerCredentials.params("oauth_nonce"),
            marketplaceCredentials = connectorCredentials
          )
          val expectedOAuthTokenParameters = oauthSignatureParser.parse(expectedOAuthToken)

          if (expectedOAuthTokenParameters.oauthSignature == callerCredentials.params("oauth_signature")) {
            Right(requestClientKey)
          } else {
            Left(HttpChallenge(scheme = "OAuth", realm = None))
          }
        case _ =>
          Left(HttpChallenge(scheme = "OAuth", realm = None))
      }
    }
}
