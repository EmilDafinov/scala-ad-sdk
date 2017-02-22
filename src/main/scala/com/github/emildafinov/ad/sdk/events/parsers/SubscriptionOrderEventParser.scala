package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionOrderEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionOrder
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionOrderEventParser {
  def apply(): Event => SubscriptionOrder =
    event => SubscriptionOrderEvent(id = event.id)
}
