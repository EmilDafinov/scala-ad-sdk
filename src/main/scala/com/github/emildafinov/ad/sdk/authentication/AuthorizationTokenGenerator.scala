package com.github.emildafinov.ad.sdk.authentication

import com.typesafe.scalalogging.StrictLogging
import oauth.signpost.OAuthConsumer
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods
import org.apache.http.client.methods._

class AuthorizationTokenGenerator extends StrictLogging {
  private val authorizationHeaderName = "Authorization"

  /**
    * For a pair of HTTP method name and url, generate the appropriate "Authorization" header value
    *
    * @param httpMethodName the HTTP method name for which we need to generate an authorization value
    * @param resourceUrl    the resource for which we want to generate an authorization value
    * @return The OAuth bearer token value
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
    * @param httpMethodName
    * @param resourceUrl
    * @param timeStamp
    * @param nonce
    * @param marketplaceCredentials
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
      case requestMethod if requestMethod.equalsIgnoreCase("GET") => new HttpGet(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("POST") => new HttpPost(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("PUT") => new HttpPut(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("DELETE") => new HttpDelete(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("HEAD") => new HttpHead(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("OPTIONS") => new HttpOptions(resourceUrl)
      case requestMethod if requestMethod.equalsIgnoreCase("TRACE") => new HttpTrace(resourceUrl)
      case _ => throw new IllegalArgumentException(s"Request method $httpMethodName is not supported")
    }

    consumer.sign(request)

    request.getHeaders(authorizationHeaderName)(0).getValue
  }
}
