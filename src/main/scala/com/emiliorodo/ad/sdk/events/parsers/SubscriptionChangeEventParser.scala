package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionChangeEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionChange
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionChangeEventParser {
  def apply(): Event => SubscriptionChange =
    event => SubscriptionChangeEvent(event.id)
}
