package com.github.emildafinov.ad.sdk.event.parsers

import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.payload.Event

trait RichEventParsersModule {
  lazy val subscriptionOrderEventParser: (Event, String ) => SubscriptionOrder = SubscriptionOrderEventParser()
  lazy val subscriptionCancelEventParser: (Event, String ) => SubscriptionCancel = SubscriptionCancelEventParser()
  lazy val subscriptionChangeEventParser: (Event, String ) => SubscriptionChange = SubscriptionChangeEventParser()
  lazy val subscriptionClosedEventParser: (Event, String ) => SubscriptionClosed = SubscriptionClosedEventParser()
  lazy val subscriptionDeactivatedEventParser: (Event, String ) => SubscriptionDeactivated = SubscriptionDeactivatedEventParser()
  lazy val subscriptionReactivatedEventParser: (Event, String ) => SubscriptionReactivated = SubscriptionReactivatedEventParser()
  lazy val subscriptionUpcomingInvoiceEventParser: (Event, String ) => SubscriptionUpcomingInvoice = SubscriptionUpcomingInvoiceParser()
  lazy val userAssignmentEventParser: (Event, String ) => UserAssignment = ???
  lazy val userUnassignmentEventParser: (Event, String ) => UserUnassignment = ???
  lazy val addonSubscriptionOrderEventParser: (Event, String ) => AddonSubscriptionOrder = ???
  lazy val addonSubscriptionCancelEventParser: (Event, String ) => AddonSubscriptionCancel = ???
}

object UserAssignmentEventParser {
  def apply(): Event => UserAssignment = ???
}

object UserUnassignmentEventParser {
  def apply(): Event => UserUnassignment = ???  
}













