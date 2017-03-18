package com.github.emildafinov.ad.sdk.payload

object EventType extends Enumeration {
  type EventType = Value
  val SUBSCRIPTION_ORDER, 
      SUBSCRIPTION_CANCEL, 
      SUBSCRIPTION_CHANGE, 
      SUBSCRIPTION_NOTICE, 
      USER_ASSIGNMENT, 
      USER_UNASSIGNMENT = Value
}
