package com.github.emildafinov.ad.sdk

import org.scalatest.matchers.{BeMatcher, MatchResult}

class ValidOauth1TokenMatcher(customerKey: String) extends BeMatcher[String] {
  override def apply(oauthSignatureString: String): MatchResult =
    MatchResult (
      oauthSignatureString matches s"""OAuth oauth_consumer_key="$customerKey", oauth_nonce=".*", oauth_signature=".*", oauth_signature_method="HMAC-SHA1", oauth_timestamp=".*", oauth_version="1.0"""",
      "the string is not a valid Oauth signature",
      "the string is a valid Oauth signature"
    )
}
