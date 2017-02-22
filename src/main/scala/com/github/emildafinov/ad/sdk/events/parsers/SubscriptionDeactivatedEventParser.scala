package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionDeactivatedEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionDeactivated
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionDeactivatedEventParser {
  def apply(): Event => SubscriptionDeactivated =
    event => SubscriptionDeactivatedEvent(event.id)
}
