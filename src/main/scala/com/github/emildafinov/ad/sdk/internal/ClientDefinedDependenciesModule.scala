package com.github.emildafinov.ad.sdk.internal

import com.github.emildafinov.ad.sdk.EventHandler
import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.event.responses._

/**
  * Abstract trait whose implementation should define all the dependencies that a user of the SDK can
  * implmenent
  */
private[sdk] trait ClientDefinedDependenciesModule {
  val subscriptionOrderHandler: EventHandler[SubscriptionOrder]
  val subscriptionCancelHandler: EventHandler[SubscriptionCancel]
  val subscriptionChangeHandler: EventHandler[SubscriptionChange]
  val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel]
  val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder]
  val userAssignedHandler: EventHandler[UserAssignment]
  val userUnassignedHandler: EventHandler[UserUnassignment]
  val subscriptionClosedHandler: EventHandler[SubscriptionClosed]
  val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated]
  val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated]
  val subscriptionUpcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice]
}


