package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionOrderEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionOrder
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionOrderEventParser {
  def apply(): Event => SubscriptionOrder =
    event => SubscriptionOrderEvent(id = event.id)
}
