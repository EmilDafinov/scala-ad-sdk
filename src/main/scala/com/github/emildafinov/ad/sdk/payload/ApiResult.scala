package com.github.emildafinov.ad.sdk.payload

case class ApiResult(success: Boolean = true,
                     message: String = "",
                     errorCode: String = "",
                     accountIdentifier: String,
                     userIdentifier: String)

object ApiResults {
  def success() = ???

  def failure(message: String): ApiResult = ???
  
  def unknownError(): ApiResult = ApiResult(
    success = false,
    message = "An unknown error has occurred",
    errorCode = "UNKNOWN_ERROR",
    accountIdentifier = "",
    userIdentifier = ""
  )
}
