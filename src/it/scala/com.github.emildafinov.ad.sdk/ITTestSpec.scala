package com.github.emildafinov.ad.sdk

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.SpanSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

trait ITTestSpec extends FlatSpec 
  with Matchers 
  with MockitoSugar 
  with ScalaFutures
  with SpanSugar
  with BeforeAndAfter
  with AkkaSpec 
  with WiremockHttpServiceTestSuite
