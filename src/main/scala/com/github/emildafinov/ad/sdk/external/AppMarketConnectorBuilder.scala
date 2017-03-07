package com.github.emildafinov.ad.sdk.external

import com.github.emildafinov.ad.sdk.authentication.CredentialsSupplier
import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.event.responses._
import com.github.emildafinov.ad.sdk.internal._
import com.github.emildafinov.ad.sdk.{AppMarketConnector, ConnectorRootApplicationContext, ConnectorStarter, EventHandler}

/**
  * Main entry point of the SDK. A client would use this class to construct their own
  * [[AppMarketConnector]] instance. All required client dependencies are required arguments
  * of the constructor. For the optional ones can be set after the [[AppMarketConnectorBuilder()]]
  * instantiation. If a given optional dependency is not set by the user, a default one 
  * would be used.
  * 
  * @param subscriptionOrderHandler defines the client-specific behavior to be executed when receiving a 
  *                                 "Subscription Order" event
  * @param subscriptionCancelHandler defines the client-specific behavior to be executed when receiving a 
  *                                 "Subscription Cancel" event
  * @param credentialsSupplier  allows the retrieval of the client secret shared between the connector and the 
  *                             marketplace for a given client key.
  */
class AppMarketConnectorBuilder(subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse],
                                subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse],
                                credentialsSupplier: CredentialsSupplier) {

  require(subscriptionOrderHandler != null)
  require(subscriptionCancelHandler != null)

  private var subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = UnimplementedEventHandler(classOf[SubscriptionChange])
  private var addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse] = UnimplementedEventHandler(classOf[AddonSubscriptionCancel])
  private var addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse] = UnimplementedEventHandler(classOf[AddonSubscriptionOrder])
  private var userAssignedHandler: EventHandler[UserAssignment, UserAssignmentResponse] = UnimplementedEventHandler(classOf[UserAssignment])
  private var userUnassignedHandler: EventHandler[UserUnassignment, UserUnassignmentResponse] = UnimplementedEventHandler(classOf[UserUnassignment])
  private var subscriptionClosedHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse] = UnimplementedEventHandler(classOf[SubscriptionClosed])
  private var subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse] = UnimplementedEventHandler(classOf[SubscriptionDeactivated])
  private var subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse] = UnimplementedEventHandler(classOf[SubscriptionReactivated])
  private var upcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse] = UnimplementedEventHandler(classOf[SubscriptionUpcomingInvoice])
  
  def subscriptionChangeHandler(eventHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionChangeHandler = eventHandler
    this
  }

  def addonSubscriptionCancelHandler(eventHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionCancelHandler = eventHandler
    this
  }

  def addonSubscriptionOrderHandler(eventHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionOrderHandler = eventHandler
    this
  }

  def userAssignedHandler(eventHandler: EventHandler[UserAssignment, UserAssignmentResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userAssignedHandler = eventHandler
    this
  }

  def userUnassignedHandler(eventHandler: EventHandler[UserUnassignment, UserUnassignmentResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userUnassignedHandler = eventHandler
    this
  }

  def subscriptionClosedHandler(eventHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionClosedHandler = eventHandler
    this
  }

  def subscriptionDeactivatedHandler(eventHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionDeactivatedHandler = eventHandler
    this
  }

  def subscriptionReactivatedHandler(eventHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionReactivatedHandler = eventHandler
    this
  }

  def upcomingInvoiceHandler(eventHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    upcomingInvoiceHandler = eventHandler
    this
  }

  def build(): AppMarketConnector = {
    new ClientDefinedDependenciesModule with ConnectorRootApplicationContext with ConnectorStarter with AppMarketConnector {
      override val subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse] = AppMarketConnectorBuilder.this.subscriptionCancelHandler
      override val userAssignedHandler: EventHandler[UserAssignment, UserAssignmentResponse] = AppMarketConnectorBuilder.this.userAssignedHandler
      override val subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = AppMarketConnectorBuilder.this.subscriptionChangeHandler
      override val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse] = AppMarketConnectorBuilder.this.addonSubscriptionCancelHandler
      override val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse] = AppMarketConnectorBuilder.this.addonSubscriptionOrderHandler
      override val subscriptionClosedHandler: EventHandler[SubscriptionClosed, SubscriptionClosedResponse] = AppMarketConnectorBuilder.this.subscriptionClosedHandler
      override val subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse] = AppMarketConnectorBuilder.this.subscriptionOrderHandler
      override val userUnassignedHandler: EventHandler[UserUnassignment, UserUnassignmentResponse] = AppMarketConnectorBuilder.this.userUnassignedHandler
      override val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated, SubscriptionReactivatedResponse] = AppMarketConnectorBuilder.this.subscriptionReactivatedHandler
      override val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated, SubscriptionDeactivatedResponse] = AppMarketConnectorBuilder.this.subscriptionDeactivatedHandler
      override val subscriptionUpcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice, SubscriptionUpcomingInvoiceResponse] = AppMarketConnectorBuilder.this.upcomingInvoiceHandler
      override val credentialsSupplier: CredentialsSupplier = AppMarketConnectorBuilder.this.credentialsSupplier
    }
  }
}
