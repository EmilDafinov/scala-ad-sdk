package com.github.emildafinov.ad.sdk.event.parsers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionDeactivated, SubscriptionDeactivatedEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionDeactivatedEventParser {
  def apply(): (Event, String) => SubscriptionDeactivated =
    (event, eventId) => SubscriptionDeactivatedEvent(eventId)
}
