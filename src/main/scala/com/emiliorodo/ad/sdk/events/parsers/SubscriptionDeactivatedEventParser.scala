package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionDeactivatedEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionDeactivated
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionDeactivatedEventParser {
  def apply(): Event => SubscriptionDeactivated =
    event => SubscriptionDeactivatedEvent(event.id)
}
