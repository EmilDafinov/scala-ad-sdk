package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.SubscriptionUpcomingInvoiceEvent
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionUpcomingInvoice
import com.emiliorodo.ad.sdk.payload.Event

object SubscriptionUpcomingInvoiceParser {
  def apply(): Event => SubscriptionUpcomingInvoice =
    event => SubscriptionUpcomingInvoiceEvent(event.id)
}
