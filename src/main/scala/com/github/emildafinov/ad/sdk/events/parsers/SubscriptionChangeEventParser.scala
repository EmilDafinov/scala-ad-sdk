package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionChangeEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionChange
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionChangeEventParser {
  def apply(): Event => SubscriptionChange =
    event => SubscriptionChangeEvent(event.id)
}
