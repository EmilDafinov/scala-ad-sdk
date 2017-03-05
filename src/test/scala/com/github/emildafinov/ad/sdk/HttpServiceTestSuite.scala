package com.github.emildafinov.ad.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Suite}

trait HttpServiceTestSuite extends UnitTestSpec with BeforeAndAfterAll {
  this: FlatSpec =>

  val dummyUrl = "http://example.org"

  val mockHttpServer: WireMockServer = new WireMockServer(
    options()
      .dynamicPort().dynamicHttpsPort()
  )
  
  override def beforeAll(): Unit = {
    mockHttpServer.start()
  }

  override def afterAll(): Unit = {
    mockHttpServer.stop()
  }
}
