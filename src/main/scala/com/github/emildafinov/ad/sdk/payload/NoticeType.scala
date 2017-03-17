package com.github.emildafinov.ad.sdk.payload

object NoticeType extends Enumeration {
  type NoticeType = Value
  val CLOSED, 
      DEACTIVATED, 
      REACTIVATED, 
      UPCOMING_INVOICE = Value
}


