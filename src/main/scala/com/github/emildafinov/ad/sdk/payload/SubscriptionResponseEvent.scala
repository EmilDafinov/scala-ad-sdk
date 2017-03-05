package com.github.emildafinov.ad.sdk.payload

import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse

import scala.beans.BeanProperty

case class SubscriptionResponseEvent(@BeanProperty userIdentifier: String,
                                     @BeanProperty accountIdentifier: String) extends SubscriptionOrderResponse
