package com.emiliorodo

import com.google.common.base.Charsets.UTF_8
import com.google.common.io.Resources.{getResource, toString => asString}

import scala.language.postfixOps

/**
  * @author edafinov
  */
package object ad {

  def resourceString(testResource: String): String = {
    asString(getResource(testResource), UTF_8)
  }
}
