package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionClosedEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionClosed
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionClosedEventParser {
  def apply(): Event => SubscriptionClosed =
    event => SubscriptionClosedEvent(event.id)
}
