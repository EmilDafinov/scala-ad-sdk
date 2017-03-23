package com.github.emildafinov.ad.sdk.authentication

import akka.http.scaladsl.model.headers.{HttpChallenge, HttpCredentials}
import akka.http.scaladsl.server.Directives

import scala.concurrent.{ExecutionContext, Future}
import Directives._
class OAuthAuthenticatorFactory(credentialsSupplier: AppMarketCredentialsSupplier,
                                authorizationTokenGenerator: AuthorizationTokenGenerator,
                                oauthSignatureParser: OauthSignatureParser) {

  def authenticatorFunction(requestHttpMethodName: String, requestUrl: String)
                           (credentialsInRequest: Option[HttpCredentials])
                           (implicit ex: ExecutionContext): Future[AuthenticationResult[AppMarketCredentials]] =
    Future {
      credentialsInRequest match {
        case Some(callerCredentials) =>

          val requestClientKey = callerCredentials.params("oauth_consumer_key")
          val connectorCredentials = credentialsSupplier
            .readCredentialsFor(requestClientKey)
            .orElseThrow(() => new UnknownClientKeyException())
          val expectedOAuthTokenParameters = expectedOauthParameters(
            requestHttpMethodName, 
            requestUrl,
            callerCredentials.params("oauth_timestamp"), 
            callerCredentials.params("oauth_nonce"), 
            connectorCredentials
          )

          if (expectedOAuthTokenParameters.oauthSignature == callerCredentials.params("oauth_signature")) {
            Right(connectorCredentials)
          } else {
            Left(HttpChallenge(scheme = "OAuth", realm = None))
          }
        case _ =>
          Left(HttpChallenge(scheme = "OAuth", realm = None))
      }
    } recover {
      case _: UnknownClientKeyException => Left(HttpChallenge(scheme = "OAuth", realm = None))
    }

  private def expectedOauthParameters(requestHttpMethodName: String, 
                                      requestUrl: String,
                                      callerTimestamp: String,
                                      callerNonce: String,
                                      connectorCredentials: AppMarketCredentials) = {
    val expectedOAuthToken = authorizationTokenGenerator.generateAuthorizationHeader(
      httpMethodName = requestHttpMethodName,
      resourceUrl = requestUrl,
      timeStamp = callerTimestamp,
      nonce = callerNonce,
      marketplaceCredentials = connectorCredentials
    )
    oauthSignatureParser.parse(expectedOAuthToken)
  }
}
