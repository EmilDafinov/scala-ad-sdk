package com.github.emildafinov.ad.sdk.event.parsers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionReactivated, SubscriptionReactivatedEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionReactivatedEventParser {
  def apply(): (Event, String) => SubscriptionReactivated  =
    (event, eventId) => SubscriptionReactivatedEvent(eventId)
}
