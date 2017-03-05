package com.github.emildafinov.ad.sdk.event.parsers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionCancel, SubscriptionCancelEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionCancelEventParser {
  def apply(): (Event, String) => SubscriptionCancel =
    (event, eventId) => SubscriptionCancelEvent(eventId)
}
