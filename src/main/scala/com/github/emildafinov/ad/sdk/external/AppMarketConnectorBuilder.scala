package com.github.emildafinov.ad.sdk.external

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.event.responses._
import com.github.emildafinov.ad.sdk.internal._
import com.github.emildafinov.ad.sdk.http.server.ConnectorServerStarter
import com.github.emildafinov.ad.sdk.{AppMarketConnector, ConnectorRootApplicationContext, EventHandler}

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
class AppMarketConnectorBuilder(subscriptionOrderHandler: EventHandler[SubscriptionOrder],
                                subscriptionCancelHandler: EventHandler[SubscriptionCancel],
                                credentialsSupplier: AppMarketCredentialsSupplier) {

  require(subscriptionOrderHandler != null)
  require(subscriptionCancelHandler != null)

  private var subscriptionChangeHandler: EventHandler[SubscriptionChange] = UnimplementedEventHandler(classOf[SubscriptionChange])
  private var addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel] = UnimplementedEventHandler(classOf[AddonSubscriptionCancel])
  private var addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder] = UnimplementedEventHandler(classOf[AddonSubscriptionOrder])
  private var userAssignedHandler: EventHandler[UserAssignment] = UnimplementedEventHandler(classOf[UserAssignment])
  private var userUnassignedHandler: EventHandler[UserUnassignment] = UnimplementedEventHandler(classOf[UserUnassignment])
  private var subscriptionClosedHandler: EventHandler[SubscriptionClosed] = UnimplementedEventHandler(classOf[SubscriptionClosed])
  private var subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated] = UnimplementedEventHandler(classOf[SubscriptionDeactivated])
  private var subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated] = UnimplementedEventHandler(classOf[SubscriptionReactivated])
  private var upcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice] = UnimplementedEventHandler(classOf[SubscriptionUpcomingInvoice])
  
  def subscriptionChangeHandler(eventHandler: EventHandler[SubscriptionChange]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionChangeHandler = eventHandler
    this
  }

  def addonSubscriptionCancelHandler(eventHandler: EventHandler[AddonSubscriptionCancel]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionCancelHandler = eventHandler
    this
  }

  def addonSubscriptionOrderHandler(eventHandler: EventHandler[AddonSubscriptionOrder]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionOrderHandler = eventHandler
    this
  }

  def userAssignedHandler(eventHandler: EventHandler[UserAssignment]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userAssignedHandler = eventHandler
    this
  }

  def userUnassignedHandler(eventHandler: EventHandler[UserUnassignment]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userUnassignedHandler = eventHandler
    this
  }

  def subscriptionClosedHandler(eventHandler: EventHandler[SubscriptionClosed]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionClosedHandler = eventHandler
    this
  }

  def subscriptionDeactivatedHandler(eventHandler: EventHandler[SubscriptionDeactivated]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionDeactivatedHandler = eventHandler
    this
  }

  def subscriptionReactivatedHandler(eventHandler: EventHandler[SubscriptionReactivated]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionReactivatedHandler = eventHandler
    this
  }

  def upcomingInvoiceHandler(eventHandler: EventHandler[SubscriptionUpcomingInvoice]): AppMarketConnectorBuilder = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    upcomingInvoiceHandler = eventHandler
    this
  }

  def build(): AppMarketConnector = {
    new ClientDefinedDependenciesModule with ConnectorRootApplicationContext with ConnectorServerStarter with AppMarketConnector {
      override val subscriptionCancelHandler: EventHandler[SubscriptionCancel] = AppMarketConnectorBuilder.this.subscriptionCancelHandler
      override val userAssignedHandler: EventHandler[UserAssignment] = AppMarketConnectorBuilder.this.userAssignedHandler
      override val subscriptionChangeHandler: EventHandler[SubscriptionChange] = AppMarketConnectorBuilder.this.subscriptionChangeHandler
      override val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel] = AppMarketConnectorBuilder.this.addonSubscriptionCancelHandler
      override val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder] = AppMarketConnectorBuilder.this.addonSubscriptionOrderHandler
      override val subscriptionClosedHandler: EventHandler[SubscriptionClosed] = AppMarketConnectorBuilder.this.subscriptionClosedHandler
      override val subscriptionOrderHandler: EventHandler[SubscriptionOrder] = AppMarketConnectorBuilder.this.subscriptionOrderHandler
      override val userUnassignedHandler: EventHandler[UserUnassignment] = AppMarketConnectorBuilder.this.userUnassignedHandler
      override val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated] = AppMarketConnectorBuilder.this.subscriptionReactivatedHandler
      override val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated] = AppMarketConnectorBuilder.this.subscriptionDeactivatedHandler
      override val subscriptionUpcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice] = AppMarketConnectorBuilder.this.upcomingInvoiceHandler
      override val credentialsSupplier: AppMarketCredentialsSupplier = AppMarketConnectorBuilder.this.credentialsSupplier
    }
  }
}
