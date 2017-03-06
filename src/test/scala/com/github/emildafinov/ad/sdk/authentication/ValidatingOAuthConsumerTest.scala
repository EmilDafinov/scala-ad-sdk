package com.github.emildafinov.ad.sdk.authentication

import com.github.emildafinov.ad.sdk.UnitTestSpec
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import org.apache.http.client.methods.HttpGet

import scala.language.postfixOps

class ValidatingOAuthConsumerTest extends UnitTestSpec {

  behavior of "ValidatingOAuthConsumer"

  val testSecret = "testSecret"
  val testKey = "testKey"
  val testResourceUrl = "http://example.com"
  val authorizationHeaderName = "Authorization"
  
  def incomingGetRequestBearerToken(testKey: String, testSecret: String, testResourceUrl: String): String = {
    val encodingConsumer = new CommonsHttpOAuthConsumer(testKey, testSecret)
    val incomingRequest = encodingConsumer.sign(new HttpGet(testResourceUrl))
    incomingRequest.getHeader(authorizationHeaderName)
  }
  
  it should "produce the same signature as the one of the incoming Get request" in {
    //Given
    val incomingRequestAuthorizationHeader = incomingGetRequestBearerToken(testKey, testSecret, testResourceUrl)
    
    val signatureParser = new OauthSignatureParser()
    val receivedRequestSignature = signatureParser.parse(incomingRequestAuthorizationHeader)
    val tested = new ValidatingOAuthConsumer(
      testKey, 
      testSecret, 
      receivedRequestSignature.nonce, 
      receivedRequestSignature.timestamp
    )

    val controlAuthorizationHeader = tested
      .sign(new HttpGet(testResourceUrl))
      .getHeader(authorizationHeaderName)

    //When
    val controlAuthorizationHeaderSignature = signatureParser.parse(controlAuthorizationHeader)

    //Then
    receivedRequestSignature shouldEqual controlAuthorizationHeaderSignature
  }

}
