package com.emiliorodo.ad.sdk.internal

import com.emiliorodo.ad.sdk.events.handlers.EventHandler
import com.emiliorodo.ad.sdk.events.payloads.events._
import com.emiliorodo.ad.sdk.events.payloads.responses._

private[sdk] trait ClientDefinedEventHandlersModule {
  val subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse]
  val subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse]
  val subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse]
  val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse]
  val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse]
  val userAssignedHandler: EventHandler[UserAssignment, UserAssignmentResponse]
  val userUnassignedHandler: EventHandler[UserUnassignment, UserUnassignmentResponse]
  val subscriptionClosedHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse]
  val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse]
  val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse]
  val upcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse]
}
