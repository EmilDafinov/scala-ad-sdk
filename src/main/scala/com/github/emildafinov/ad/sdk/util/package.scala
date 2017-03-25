package com.github.emildafinov.ad.sdk

import scala.io.Source

package object util {
  def readResourceFile(resourcePath: String) = {
    val expectedEventPayloadJson = Source.fromURL(getClass.getResource(resourcePath)).mkString
    expectedEventPayloadJson
  }
}
