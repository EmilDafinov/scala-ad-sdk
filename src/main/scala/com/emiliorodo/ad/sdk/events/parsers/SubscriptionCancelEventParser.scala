package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionCancelEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionCancel
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionCancelEventParser {
  def apply(): Event => SubscriptionCancel = 
    event => SubscriptionCancelEvent(event.id)
}
