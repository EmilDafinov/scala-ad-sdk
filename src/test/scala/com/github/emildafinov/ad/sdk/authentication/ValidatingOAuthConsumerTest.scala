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
    val testResourceUrl = "http://example.com"
    
    val encodingConsumer = new CommonsHttpOAuthConsumer(testKey, testSecret)
    val incomingRequest = encodingConsumer.sign(new HttpGet(testResourceUrl))
    val incomingRequestAuthorizationHeader = incomingRequest.getHeader("Authorization")
    
    val signatureParser = new OauthSignatureParser()
    val receivedRequestSignature = signatureParser.parse(incomingRequestAuthorizationHeader)
    val tested = new ValidatingOAuthConsumer(testKey, testSecret, receivedRequestSignature.nonce, receivedRequestSignature.timestamp)

    val controlAuthorizationHeader = tested
      .sign(new HttpGet(testResourceUrl))
      .getHeader("Authorization")

    //When
    val controlAuthorizationHeaderSignature = signatureParser.parse(controlAuthorizationHeader)
    
    //Then
    receivedRequestSignature shouldEqual controlAuthorizationHeaderSignature
  }

}
