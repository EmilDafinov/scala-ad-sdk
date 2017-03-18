package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.{AddonSubscriptionCancel, AddonSubscriptionCancelEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object AddonSubscriptionCancelParser {
  def apply(): (Event, String) => AddonSubscriptionCancel =
    (event, eventId) => AddonSubscriptionCancelEvent()
}
