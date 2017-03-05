package com.github.emildafinov.ad.sdk.internal

import com.github.emildafinov.ad.sdk.EventHandler
import com.github.emildafinov.ad.sdk.authentication.CredentialsSupplier
import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.event.responses._

private[sdk] trait ClientDefinedDependenciesModule {
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
  val subscriptionUpcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse]
  
  val credentialsSupplier: CredentialsSupplier
}
