package com.github.emildafinov.ad.sdk

trait CustomMatchers {

  def aValidOauthBearerTokenForConsumerKey(consumerKey: String) = 
    new ValidOauth1TokenMatcher(consumerKey)
}
