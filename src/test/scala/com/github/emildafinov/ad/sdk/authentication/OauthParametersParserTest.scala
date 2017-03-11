package com.github.emildafinov.ad.sdk.authentication

import com.github.emildafinov.ad.sdk.UnitTestSpec

class OauthParametersParserTest extends UnitTestSpec {

  behavior of "OauthSignatureParser"
  
  val tested = new OauthSignatureParser()
  
  it should "parse the OAuth signature" in {
    //Given
    val testAuthenticationValue = "OAuth oauth_consumer_key=\"testKey\", oauth_nonce=\"-6648113452900324504\", oauth_signature=\"i26Xv8qkKvXLoESFNkV9E85BXHg%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1488423242\", oauth_version=\"1.0\""
    val expectedSignature = OauthParameters(
      signatureMethod = "HMAC-SHA1",
      oauthSignature = "i26Xv8qkKvXLoESFNkV9E85BXHg%3D",
      version = "1.0",
      timestamp = "1488423242",
      consumerKey = "testKey",
      nonce = "-6648113452900324504"
    )
    
    //When
    val parsedSignature = tested.parse(testAuthenticationValue)
    
    //Then
    parsedSignature shouldEqual expectedSignature
  }
  
  it should "throw an IllegalArgumentException" in {
    //Given
    val illegalOauth1Signature = "OAuth oauth_consumer_key=\"testKey\", oauth_nonce=\"-6648113452900324504\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1488423242\", oauth_version=\"1.0\""
    
    //Then
    an [IllegalArgumentException] shouldBe thrownBy {
      //When
      tested.parse(illegalOauth1Signature)
    }
  }
}
