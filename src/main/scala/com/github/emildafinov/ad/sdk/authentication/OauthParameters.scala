package com.github.emildafinov.ad.sdk.authentication

import scala.language.postfixOps

case class OauthParameters(signatureMethod: String = "HMAC-SHA1",
                           oauthSignature: String,
                           version: String = "1.0",
                           timestamp: String,
                           consumerKey: String,
                           nonce: String) {
  
  require(signatureMethod nonEmpty)
  require(oauthSignature nonEmpty)
  require(version nonEmpty)
  require(timestamp nonEmpty)
  require(consumerKey nonEmpty)
  require(nonce nonEmpty)
}
