package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionUpcomingInvoiceEvent
import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionUpcomingInvoice
import com.github.emildafinov.ad.sdk.payload.Event

object SubscriptionUpcomingInvoiceParser {
  def apply(): Event => SubscriptionUpcomingInvoice =
    event => SubscriptionUpcomingInvoiceEvent(event.id)
}
