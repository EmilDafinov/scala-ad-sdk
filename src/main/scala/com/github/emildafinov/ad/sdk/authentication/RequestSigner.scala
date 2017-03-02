package com.github.emildafinov.ad.sdk.authentication

import akka.http.scaladsl.model.HttpRequest

trait RequestSigner {
  def sign(unsignedHttpRequest: HttpRequest): HttpRequest
}
