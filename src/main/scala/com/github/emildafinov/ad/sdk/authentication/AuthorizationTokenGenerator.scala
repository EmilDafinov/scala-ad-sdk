package com.github.emildafinov.ad.sdk.authentication

import com.typesafe.scalalogging.StrictLogging
import oauth.signpost.OAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods._

class AuthorizationTokenGenerator extends StrictLogging {
  private val authorizationHeaderName = "Authorization"

  /**
    * For a pair of HTTP method name and url, generate the appropriate "Authorization" header value
    *
    * @param httpMethodName the HTTP method name for which we need to generate an authorization value
    * @param resourceUrl    the resource for which we want to generate an authorization value
    * @return The OAuth 1.0 token value
    */
  def generateAuthorizationHeader(httpMethodName: String,
                                  resourceUrl: String,
                                  marketplaceCredentials: MarketplaceCredentials): String = {
    signWithConsumer(
      new CommonsHttpOAuthConsumer(
        marketplaceCredentials.clientKey(),
        marketplaceCredentials.clientSecret()
      )
    )(httpMethodName, resourceUrl, marketplaceCredentials)
  }

  /**
    * Generate the signature, with a pre-defined value of the timestamp and the nonce.
    * Useful for validating incoming requests
    *
    * @param httpMethodName the method name used to sign
    * @param resourceUrl the url used to sign
    * @param timeStamp the timestamp used to sign (usually taken from the request we want to validate)
    * @param nonce the nonce used to sign (usually taken from the request we want to validate)
    * @param marketplaceCredentials the key/secret pair that we use to sign
    * @return
    */
  def generateAuthorizationHeader(httpMethodName: String,
                                  resourceUrl: String,
                                  timeStamp: String,
                                  nonce: String,
                                  marketplaceCredentials: MarketplaceCredentials): String = 
    signWithConsumer(
      new ValidatingOAuthConsumer(
        consumerKey = marketplaceCredentials.clientKey(),
        consumerSecret = marketplaceCredentials.clientSecret(),
        nonce = nonce,
        timestamp = timeStamp
      )
    )(httpMethodName, resourceUrl, marketplaceCredentials)

  private def signWithConsumer(consumer: OAuthConsumer)
                              (httpMethodName: String,
                               resourceUrl: String,
                               marketplaceCredentials: MarketplaceCredentials): String = {

    val request = httpMethodName match {
      case requestMethod if requestMethod.equalsIgnoreCase(HttpGet.METHOD_NAME) => new HttpGet(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpPost.METHOD_NAME) => new HttpPost(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpPut.METHOD_NAME) => new HttpPut(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpDelete.METHOD_NAME) => new HttpDelete(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpHead.METHOD_NAME) => new HttpHead(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpOptions.METHOD_NAME) => new HttpOptions(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase(HttpTrace.METHOD_NAME) => new HttpTrace(resourceUrl)
      case _ => throw new IllegalArgumentException(s"Request method $httpMethodName is not supported")
    }

    consumer.sign(request)

    request.getHeaders(authorizationHeaderName)(0).getValue
  }
}
