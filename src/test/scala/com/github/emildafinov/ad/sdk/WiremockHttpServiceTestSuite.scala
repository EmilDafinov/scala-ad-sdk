package com.github.emildafinov.ad.sdk

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import org.scalatest.{BeforeAndAfterAll, Suite}

/**
  * Utilities for testing HTTP clients. Provides a HTTP server mock implemented using
  * Wiremock http://wiremock.org/.
  * As 
  */
trait WiremockHttpServiceTestSuite extends BeforeAndAfterAll {
  this: Suite =>

  val httpServerMock: WireMockServer = new WireMockServer(
    options()
      .dynamicPort().dynamicHttpsPort()
  )
  
  override def beforeAll(): Unit = {
    super.beforeAll()
    httpServerMock.start()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    httpServerMock.stop()
  }
}
