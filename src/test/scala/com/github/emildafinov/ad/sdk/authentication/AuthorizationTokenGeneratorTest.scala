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
  val mockHttpRequest: HttpRequest = mock [oauth.signpost.http.HttpRequest]
  val sampleConsumerKey = "sampleKey"
  val sampleConsumerSecret = "sampleSecret"
  val testedService = new AuthorizationTokenGenerator()

  val mockCredentials = AppMarketCredentials(
    clientKey = sampleConsumerKey,
    clientSecret = sampleConsumerSecret
  )
  
  it should "sign a GET request" in {
    //Given
    val testHTTPMethod = "GET"
    val testUrl = "http://www.google.com"
    
    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName =  testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a PUT request" in {
    //Given
    val testHTTPMethod = "PUT"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a POST request" in {
    //Given
    val testHTTPMethod = "POST"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a DELETE request" in {
    //Given
    val testHTTPMethod = "DELETE"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a HEAD request" in {
    //Given
    val testHTTPMethod = "HEAD"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a OPTIONS request" in {
    //Given
    val testHTTPMethod = "OPTIONS"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }

  it should "sign a TRACE request" in {
    //Given
    val testHTTPMethod = "TRACE"
    val testUrl = "http://www.google.com"

    //When
    val (headerName, headerValue) = testedService.generateAuthorizationHeader(
      httpMethodName = testHTTPMethod,
      resourceUrl = testUrl,
      marketplaceCredentials = mockCredentials
    )

    //Then
    headerName shouldEqual "Authorization"
    headerValue shouldBe aValidOauthSignatureForConsumerKey(sampleConsumerKey)
  }
}
