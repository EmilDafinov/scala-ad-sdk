package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionOrder, SubscriptionOrderEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionOrderEventParser {
  def apply(): (Event, String) => SubscriptionOrder =
    (event, eventId) => SubscriptionOrderEvent(id = eventId)
}
