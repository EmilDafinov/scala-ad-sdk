package com.github.emildafinov.ad.sdk

trait CustomMatchers {

  def aValidOauthSignatureForConsumerKey(consumerKey: String) = 
    new ValidOauthSignatureMatcher(consumerKey)
}
