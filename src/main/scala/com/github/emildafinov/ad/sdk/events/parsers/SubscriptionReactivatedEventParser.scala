package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionReactivatedEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionReactivated
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionReactivatedEventParser {
  def apply(): Event => SubscriptionReactivated =
    event => SubscriptionReactivatedEvent(event.id)
}
