package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.payload.Event

trait RichEventParsersModule {
  lazy val subscriptionOrderEventParser: (Event, String) => SubscriptionOrder = SubscriptionOrderEventParser()
  lazy val subscriptionCancelEventParser: (Event, String) => SubscriptionCancel = SubscriptionCancelEventParser()
  lazy val subscriptionChangeEventParser: (Event, String) => SubscriptionChange = SubscriptionChangeEventParser()
  lazy val subscriptionClosedEventParser: (Event, String) => SubscriptionClosed = SubscriptionClosedEventParser()
  lazy val subscriptionDeactivatedEventParser: (Event, String) => SubscriptionDeactivated = SubscriptionDeactivatedEventParser()
  lazy val subscriptionReactivatedEventParser: (Event, String) => SubscriptionReactivated = SubscriptionReactivatedEventParser()
  lazy val subscriptionUpcomingInvoiceEventParser: (Event, String) => SubscriptionUpcomingInvoice = SubscriptionUpcomingInvoiceParser()
  lazy val userAssignmentEventParser: (Event, String) => UserAssignment = UserAssignmentEventParser()
  lazy val userUnassignmentEventParser: (Event, String) => UserUnassignment = UserUnassignmentEventParser()
  lazy val addonSubscriptionOrderEventParser: (Event, String) => AddonSubscriptionOrder = AddonSubscriptionOrderParser()
  lazy val addonSubscriptionCancelEventParser: (Event, String) => AddonSubscriptionCancel = AddonSubscriptionCancelParser()
}
