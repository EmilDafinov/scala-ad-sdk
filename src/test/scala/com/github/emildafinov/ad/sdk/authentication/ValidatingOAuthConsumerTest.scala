package com.github.emildafinov.ad.sdk.authentication

import com.github.emildafinov.ad.sdk.UnitTestSpec
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods.HttpGet

import scala.language.postfixOps





class ValidatingOAuthConsumerTest extends UnitTestSpec {
  
  behavior of "ValidatingOAuthConsumer"
  
  it should "fdsfd" in {
    //Given
    val testSecret = "testSecret"
    val testKey = "testKey"
    val resourceUrl = "http://example.com"
    val encodingConsumer = new CommonsHttpOAuthConsumer(testKey, testSecret)
    val signedRequest = encodingConsumer.sign(new HttpGet(resourceUrl))
    
    val authorizationHeader = signedRequest.getHeader("Authorization")
    
    val signatureParser = new OauthSignatureParser()
    val receivedRequestSignature = signatureParser.parse(authorizationHeader)
    val tested = new ValidatingOAuthConsumer(testKey, testSecret, receivedRequestSignature.nonce, receivedRequestSignature.timestamp)

    val controlAuthorizationHeader = tested
      .sign(new HttpGet(resourceUrl))
      .getHeader("Authorization")

    //When
    val controlAuthorizationHeaderSignature = signatureParser.parse(controlAuthorizationHeader)
    
    //Then
    receivedRequestSignature shouldEqual controlAuthorizationHeaderSignature
  }

}
