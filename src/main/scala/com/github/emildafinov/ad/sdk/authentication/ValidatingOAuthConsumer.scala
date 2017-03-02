package com.github.emildafinov.ad.sdk.authentication

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer

/**
  * A consumer that signs with a pre-defined nonce and timestamp. Allows to sign a request with the same nonce / timestamp
  * as the ones used for another request. That way, if the generated signature matches the one of the incoming request, 
  * we can be certain that the incoming request has been signed with the same secret as ours.
  * @param consumerKey our consumer key
  * @param consumerSecret our secret
  * @param nonce coming from the incoming request
  * @param timestamp coming from the incoming request
  */
class ValidatingOAuthConsumer(consumerKey: String, 
                              consumerSecret: String, 
                              nonce: String, 
                              timestamp: String) extends CommonsHttpOAuthConsumer(consumerKey, consumerSecret) {
  override def generateTimestamp(): String = timestamp
  override def generateNonce(): String = nonce
}
