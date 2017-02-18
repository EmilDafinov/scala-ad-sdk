package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionReactivatedEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionReactivated
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionReactivatedEventParser {
  def apply(): Event => SubscriptionReactivated =
    event => SubscriptionReactivatedEvent(event.id)
}
