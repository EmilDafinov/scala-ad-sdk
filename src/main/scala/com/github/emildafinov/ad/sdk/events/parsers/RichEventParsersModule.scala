package com.github.emildafinov.ad.sdk.events.parsers

import com.github.emildafinov.ad.sdk.events.payloads.events._
import com.github.emildafinov.ad.sdk.payload.Event

trait RichEventParsersModule {
  lazy val subscriptionOrderEventParser: Event => SubscriptionOrder = SubscriptionOrderEventParser()
  lazy val subscriptionCancelEventParser: Event => SubscriptionCancel = SubscriptionCancelEventParser()
  lazy val subscriptionChangeEventParser: Event => SubscriptionChange = SubscriptionChangeEventParser()
  lazy val subscriptionClosedEventParser: Event => SubscriptionClosed = SubscriptionClosedEventParser()
  lazy val subscriptionDeactivatedEventParser: Event => SubscriptionDeactivated = SubscriptionDeactivatedEventParser()
  lazy val subscriptionReactivatedEventParser: Event => SubscriptionReactivated = SubscriptionReactivatedEventParser()
  lazy val subscriptionIncomingInvoiceEventParser: Event => SubscriptionUpcomingInvoice = SubscriptionUpcomingInvoiceParser()
}














