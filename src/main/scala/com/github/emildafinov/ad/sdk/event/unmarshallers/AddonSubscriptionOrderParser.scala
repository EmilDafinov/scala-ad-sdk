package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.{AddonSubscriptionOrder, AddonSubscriptionOrderEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object AddonSubscriptionOrderParser {
  def apply(): (Event, String) => AddonSubscriptionOrder =
    (event, eventId) => AddonSubscriptionOrderEvent()
}
