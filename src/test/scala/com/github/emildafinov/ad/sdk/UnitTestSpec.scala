package com.github.emildafinov.ad.sdk

import org.scalactic.FutureSugar
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

trait UnitTestSpec extends FlatSpec with Matchers with MockitoSugar with FutureSugar
