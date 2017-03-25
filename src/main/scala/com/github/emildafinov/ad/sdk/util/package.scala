package com.github.emildafinov.ad.sdk

import scala.io.Source

package object util {
  def readResourceFile(resourcePath: String): String = {
    val bufferedSource = Source.fromURL(getClass.getResource(resourcePath))
    val result = bufferedSource.mkString
    bufferedSource.close()
    result
  }
}
