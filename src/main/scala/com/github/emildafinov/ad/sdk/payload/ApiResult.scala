package com.github.emildafinov.ad.sdk.payload

case class ApiResult(success: Boolean = true,
                     message: String = "",
                     errorCode: String = "",
                     accountIdentifier: String = "",
                     userIdentifier: String = "")

object ApiResults {
  def success() = ApiResult(
    accountIdentifier = "someAccount",
    userIdentifier = "someUser"
  )

  def failure(message: String): ApiResult = ApiResult(
    success = false,
    message = message
  )
  
  def unknownError(message: String = "An unknown error has occurred"): ApiResult = ApiResult(
    success = false,
    message = message,
    errorCode = "UNKNOWN_ERROR",
    accountIdentifier = "",
    userIdentifier = ""
  )
}
