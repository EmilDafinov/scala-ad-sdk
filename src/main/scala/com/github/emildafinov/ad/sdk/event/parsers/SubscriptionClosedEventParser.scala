package com.github.emildafinov.ad.sdk.event.parsers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionClosed, SubscriptionClosedEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionClosedEventParser {
  def apply(): (Event, String) => SubscriptionClosed =
    (event, eventId) => SubscriptionClosedEvent(eventId)
}
