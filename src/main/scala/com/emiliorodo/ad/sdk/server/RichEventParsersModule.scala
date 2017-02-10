package com.emiliorodo.ad.sdk.server

import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionOrder
import com.emiliorodo.ad.sdk.payload.{Event, SubscriptionOrderEvent}

trait RichEventParsersModule {
  lazy val subscriptionOrderEventParser: Event => SubscriptionOrder = SubscriptionOrderEventParser()
}

object SubscriptionOrderEventParser {
  def apply(): Event => SubscriptionOrder =
    event => SubscriptionOrderEvent(id = event.id)
}
