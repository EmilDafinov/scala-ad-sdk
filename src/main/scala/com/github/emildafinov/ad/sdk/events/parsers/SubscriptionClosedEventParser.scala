package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionClosedEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionClosed
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionClosedEventParser {
  def apply(): Event => SubscriptionClosed =
    event => SubscriptionClosedEvent(event.id)
}
