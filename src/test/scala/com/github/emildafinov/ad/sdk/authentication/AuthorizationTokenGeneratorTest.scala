package com.github.emildafinov.ad.sdk.authentication

import com.github.emildafinov.ad.sdk.{CustomMatchers, UnitTestSpec}
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer
import oauth.signpost.http.HttpRequest

import scala.language.postfixOps

class AuthorizationTokenGeneratorTest extends UnitTestSpec with CustomMatchers {

  behavior of "AuthorizationTokenGenerator"

  val mockConsumerKey = "mockConsumerKey"
  val mockConsumerSecret = "mockConsumerSecret"

  val mockConsumer: CommonsHttpOAuthConsumer = new CommonsHttpOAuthConsumer("", "")
  val mockHttpRequest: HttpRequest = mock[oauth.signpost.http.HttpRequest]
  val sampleConsumerKey = "sampleKey"
  val sampleConsumerSecret = "sampleSecret"
  val testedService = new AuthorizationTokenGenerator()

  val mockCredentials = AppMarketCredentialsImpl(
    clientKey = sampleConsumerKey,
    clientSecret = sampleConsumerSecret
  )

  it should "sign a GET request" in {
    //Given
    val testHTTPMethod = "GET"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a PUT request" in {
    //Given
    val testHTTPMethod = "PUT"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a POST request" in {
    //Given
    val testHTTPMethod = "POST"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a DELETE request" in {
    //Given
    val testHTTPMethod = "DELETE"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a HEAD request" in {
    //Given
    val testHTTPMethod = "HEAD"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a OPTIONS request" in {
    //Given
    val testHTTPMethod = "OPTIONS"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "sign a TRACE request" in {
    //Given
    val testHTTPMethod = "TRACE"
    val testUrl = "http://www.google.com"

    //When
    val headerValue = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerValue shouldBe aValidOauthBearerTokenForConsumerKey(sampleConsumerKey)
  }

  it should "fail signing a request with illegal name" in {
    //Given
    val testHTTPMethod = "ILLEgAl"
    val testUrl = "http://www.google.com"

    //Then
    an[IllegalArgumentException] shouldBe thrownBy {
      //When
      testedService.generateAuthorizationHeader(
        httpMethodName = testHTTPMethod,
        resourceUrl = testUrl,
        marketplaceCredentials = mockCredentials
      )
    }
  }

  it should "return matching parameters when used to sign two requests with the same http method, url, timestamp and nonce" in {

    //Given
    val testHttpMethod = "GET"
    val testResourceUrl = "http://example.com"
    val testClientId = "testCLientId"
    val testClientSecret = "testClientSecret"
    val testCredentals = AppMarketCredentialsImpl(
      clientKey = testClientId,
      clientSecret = testClientSecret
    )
    val oauthParametersParser = new OauthSignatureParser()

    val expected = testedService.generateAuthorizationHeader(
      httpMethodName = testHttpMethod,
      resourceUrl = testResourceUrl,
      marketplaceCredentials = testCredentals
    )
    val expectedParams = oauthParametersParser.parse(expected)

    //When
    val actual = testedService.generateAuthorizationHeader(
      httpMethodName = testHttpMethod,
      resourceUrl = testResourceUrl,
      marketplaceCredentials = testCredentals,
      timeStamp = expectedParams.timestamp,
      nonce = expectedParams.nonce
    )
    val actualParams = oauthParametersParser.parse(actual)


    //Then
    expectedParams shouldEqual actualParams
  }
}
