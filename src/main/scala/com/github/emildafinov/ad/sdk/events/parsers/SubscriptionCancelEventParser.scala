package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionCancelEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionCancel
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionCancelEventParser {
  def apply(): Event => SubscriptionCancel = 
    event => SubscriptionCancelEvent(event.id)
}
