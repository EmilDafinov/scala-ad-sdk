package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.{SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceEvent}
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionUpcomingInvoiceParser {
  def apply(): (Event, String) => SubscriptionUpcomingInvoice =
    (event, eventId) => SubscriptionUpcomingInvoiceEvent(eventId)
}
