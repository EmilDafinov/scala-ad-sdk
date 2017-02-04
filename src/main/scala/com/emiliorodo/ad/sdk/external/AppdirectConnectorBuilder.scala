package com.emiliorodo.ad.sdk.external

import com.emiliorodo.ad.sdk.{AppdirectConnector, ConnectorRootApplicationContext, ConnectorStarter}
import com.emiliorodo.ad.sdk.events.payloads.events._
import com.emiliorodo.ad.sdk.events.payloads.responses._
import com.emiliorodo.ad.sdk.internal.events.handlers.EventHandler
import com.emiliorodo.ad.sdk.internal.events.payloads.events._
import com.emiliorodo.ad.sdk.internal.events.payloads.responses._
import com.emiliorodo.ad.sdk.internal._

class AppdirectConnectorBuilder(subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse],
                                subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse]) {

  private var subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = new UnimplementedEventHandler(classOf[SubscriptionChange])
  private var addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse] = new UnimplementedEventHandler(classOf[AddonSubscriptionCancel])
  private var addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse] = new UnimplementedEventHandler(classOf[AddonSubscriptionOrder])
  private var userAssignedHandler: EventHandler[UserAssignment, UserAssignmentResponse] = new UnimplementedEventHandler(classOf[UserAssignment])
  private var userUnassignedHandler: EventHandler[UserUnassignment, UserUnassignmentResponse] = new UnimplementedEventHandler(classOf[UserUnassignment])
  private var subscriptionClosedHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse] = new UnimplementedEventHandler(classOf[SubscriptionClosed])
  private var subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse] = new UnimplementedEventHandler(classOf[SubscriptionDeactivated])
  private var subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse] = new UnimplementedEventHandler(classOf[SubscriptionReactivated])
  private var upcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse] = new UnimplementedEventHandler(classOf[SubscriptionUpcomingInvoice])

  def subscriptionChangeHandler(eventHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse]): AppdirectConnectorBuilder = {
    subscriptionChangeHandler = eventHandler
    this
  }

  def addonSubscriptionCancelHandler(eventHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse]): AppdirectConnectorBuilder = {
    addonSubscriptionCancelHandler = eventHandler
    this
  }

  def addonSubscriptionOrderHandler(eventHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse]): AppdirectConnectorBuilder = {
    addonSubscriptionOrderHandler = eventHandler
    this
  }

  def userAssignedHandler(eventHandler: EventHandler[UserAssignment, UserAssignmentResponse]): AppdirectConnectorBuilder = {
    userAssignedHandler = eventHandler
    this
  }

  def userUnassignedHandler(eventHandler: EventHandler[UserUnassignment, UserUnassignmentResponse]): AppdirectConnectorBuilder = {
    userUnassignedHandler = eventHandler
    this
  }

  def subscriptionClosedHandler(eventHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse]): AppdirectConnectorBuilder = {
    subscriptionClosedHandler = eventHandler
    this
  }

  def subscriptionDeactivatedHandler(eventHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse]): AppdirectConnectorBuilder = {
    subscriptionDeactivatedHandler = eventHandler
    this
  }

  def subscriptionReactivatedHandler(eventHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse]): AppdirectConnectorBuilder = {
    subscriptionReactivatedHandler = eventHandler
    this
  }

  def upcomingInvoiceHandler(eventHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse]): AppdirectConnectorBuilder = {
    upcomingInvoiceHandler = eventHandler
    this
  }

  def build(): AppdirectConnector = {
    new ClientDefinedEventHandlersModule with ConnectorRootApplicationContext with ConnectorStarter {
      override val subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse] = AppdirectConnectorBuilder.this.subscriptionCancelHandler
      override val userAssignedHandler: EventHandler[UserAssignment, UserAssignmentResponse] = AppdirectConnectorBuilder.this.userAssignedHandler
      override val subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = AppdirectConnectorBuilder.this.subscriptionChangeHandler
      override val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse] = AppdirectConnectorBuilder.this.addonSubscriptionCancelHandler
      override val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse] = AppdirectConnectorBuilder.this.addonSubscriptionOrderHandler
      override val subscriptionClosedHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse] = AppdirectConnectorBuilder.this.subscriptionClosedHandler
      override val subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse] = AppdirectConnectorBuilder.this.subscriptionOrderHandler
      override val userUnassignedHandler: EventHandler[UserUnassignment, UserUnassignmentResponse] = AppdirectConnectorBuilder.this.userUnassignedHandler
      override val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse] = AppdirectConnectorBuilder.this.subscriptionReactivatedHandler
      override val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse] = AppdirectConnectorBuilder.this.subscriptionDeactivatedHandler
      override val upcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse] = AppdirectConnectorBuilder.this.upcomingInvoiceHandler
    }
  }
}
