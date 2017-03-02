package com.github.emildafinov.ad.sdk.authentication

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.RawHeader
import com.typesafe.scalalogging.StrictLogging

/**
  * Adds an Authorization header to the input request.
  * The header value contains the Oauth1 credentials needed to authenticate with the ISV
  *
  * @param authorizationTokenGenerator a service that generates the value of the Authorization header added
  *                             to the input request
  */
class Oauth1RequestSigner(authorizationTokenGenerator: AuthorizationTokenGenerator) 
  extends RequestSigner with StrictLogging {

  def sign(unsignedHttpRequest: HttpRequest): HttpRequest = {

    val (headerName, headerValue) = authorizationTokenGenerator.generateAuthorizationHeader(
      httpMethodName = unsignedHttpRequest.method.value,
      resourceUrl = unsignedHttpRequest.uri.toString
    )
     
    unsignedHttpRequest.withHeaders(
      unsignedHttpRequest.headers :+ RawHeader(name = headerName, value = headerValue)
    )
  }
}
