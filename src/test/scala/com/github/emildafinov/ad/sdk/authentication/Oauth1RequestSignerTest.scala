package com.github.emildafinov.ad.sdk.authentication

import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.RawHeader
import com.github.emildafinov.ad.sdk.UnitTestSpec
import org.mockito.Mockito.when

import scala.language.postfixOps

class Oauth1RequestSignerTest extends UnitTestSpec {

  behavior of "Oauth1RequestSigner"

  val mockAuthorizationService: AuthorizationTokenGenerator = mock[AuthorizationTokenGenerator]

  val testedSigner = new Oauth1RequestSigner(
    authorizationTokenGenerator = mockAuthorizationService
  )

  it should "sign the input header" in {

    //Given
    val testRequestMethod = GET
    val testRequestUri = "sample.org"
    val testRequest = HttpRequest(method = testRequestMethod, uri = testRequestUri)
    val expectedHeaderValue = "expectedValue"
    when {
      mockAuthorizationService.generateAuthorizationHeader(
        httpMethodName = testRequestMethod.value,
        resourceUrl = testRequestUri
      )
    } thenReturn ("Authorization" -> expectedHeaderValue)

    //When
    val signedRequest = testedSigner.sign(testRequest)

    //Then
    signedRequest.headers contains RawHeader("Authorization", expectedHeaderValue)
  }
}
