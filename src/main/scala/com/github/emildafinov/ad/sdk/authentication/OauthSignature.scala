package com.github.emildafinov.ad.sdk.authentication

import scala.language.postfixOps

case class OauthSignature(signatureMethod: String,
                          signature: String,
                          version: String,
                          timestamp: String,
                          consumerKey: String,
                          nonce: String) {
  
  require(signatureMethod nonEmpty)
  require(signature nonEmpty)
  require(version nonEmpty)
  require(timestamp nonEmpty)
  require(consumerKey nonEmpty)
  require(nonce nonEmpty)
}
