package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionChange, SubscriptionChangeEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionChangeEventParser {
  def apply(): (Event, String) => SubscriptionChange =
    (event, eventId) => SubscriptionChangeEvent(eventId)
}
