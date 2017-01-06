package com.emiliorodo.ad.server

import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s.{NoTypeHints, jackson}

/**
  * Includes the implicit conversions to automatically serialize the results 
  * of Akka-HTTP roots as JSON. 
  * Use
  * {{{
  *   import com.emiliorodo.ad.server.JsonSupport._
  * }}}
  * 
  * in the appropriate scope to enable
  */
object JsonSupport extends Json4sSupport {
  implicit val serialization = jackson.Serialization
  implicit val formats = serialization.formats(NoTypeHints)
}
