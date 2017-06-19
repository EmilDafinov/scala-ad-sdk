package com.github.emildafinov.ad.sdk

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier
import com.github.emildafinov.ad.sdk.event.payloads._
import com.github.emildafinov.ad.sdk.http.server.ConnectorServerStarter

/**
  * Main entry point of the SDK. A client would use this class to construct their own
  * [[AppMarketConnector]] instance. All required client dependencies are required arguments
  * of the constructor. For the optional ones can be set after the [[AppMarketConnectorBuilderImpl()]]
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
class AppMarketConnectorBuilderImpl(subscriptionOrderHandler: EventHandler[SubscriptionOrder],
                                    subscriptionCancelHandler: EventHandler[SubscriptionCancel],
                                    credentialsSupplier: AppMarketCredentialsSupplier) extends AppMarketConnectorBuilder {

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
  
  override def subscriptionChangeHandler(eventHandler: EventHandler[SubscriptionChange]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionChangeHandler = eventHandler
    this
  }

  override def addonSubscriptionCancelHandler(eventHandler: EventHandler[AddonSubscriptionCancel]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionCancelHandler = eventHandler
    this
  }

  override def addonSubscriptionOrderHandler(eventHandler: EventHandler[AddonSubscriptionOrder]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    addonSubscriptionOrderHandler = eventHandler
    this
  }

  override def userAssignedHandler(eventHandler: EventHandler[UserAssignment]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userAssignedHandler = eventHandler
    this
  }

  override def userUnassignedHandler(eventHandler: EventHandler[UserUnassignment]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    userUnassignedHandler = eventHandler
    this
  }

  override def subscriptionClosedHandler(eventHandler: EventHandler[SubscriptionClosed]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionClosedHandler = eventHandler
    this
  }

  override def subscriptionDeactivatedHandler(eventHandler: EventHandler[SubscriptionDeactivated]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionDeactivatedHandler = eventHandler
    this
  }

  override def subscriptionReactivatedHandler(eventHandler: EventHandler[SubscriptionReactivated]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    subscriptionReactivatedHandler = eventHandler
    this
  }

  override def upcomingInvoiceHandler(eventHandler: EventHandler[SubscriptionUpcomingInvoice]): AppMarketConnectorBuilderImpl = {
    require(eventHandler != null, "The AppMarketplace event handlers cannot be null !")
    upcomingInvoiceHandler = eventHandler
    this
  }

  override def build(): AppMarketConnector = {
    new ClientDefinedDependenciesModule with ClientDefinedCredentialsModule with ConnectorRootApplicationContext with ConnectorServerStarter with AppMarketConnector {
      override val subscriptionCancelHandler: EventHandler[SubscriptionCancel] = AppMarketConnectorBuilderImpl.this.subscriptionCancelHandler
      override val userAssignedHandler: EventHandler[UserAssignment] = AppMarketConnectorBuilderImpl.this.userAssignedHandler
      override val subscriptionChangeHandler: EventHandler[SubscriptionChange] = AppMarketConnectorBuilderImpl.this.subscriptionChangeHandler
      override val addonSubscriptionCancelHandler: EventHandler[AddonSubscriptionCancel] = AppMarketConnectorBuilderImpl.this.addonSubscriptionCancelHandler
      override val addonSubscriptionOrderHandler: EventHandler[AddonSubscriptionOrder] = AppMarketConnectorBuilderImpl.this.addonSubscriptionOrderHandler
      override val subscriptionClosedHandler: EventHandler[SubscriptionClosed] = AppMarketConnectorBuilderImpl.this.subscriptionClosedHandler
      override val subscriptionOrderHandler: EventHandler[SubscriptionOrder] = AppMarketConnectorBuilderImpl.this.subscriptionOrderHandler
      override val userUnassignedHandler: EventHandler[UserUnassignment] = AppMarketConnectorBuilderImpl.this.userUnassignedHandler
      override val subscriptionReactivatedHandler: EventHandler[SubscriptionReactivated] = AppMarketConnectorBuilderImpl.this.subscriptionReactivatedHandler
      override val subscriptionDeactivatedHandler: EventHandler[SubscriptionDeactivated] = AppMarketConnectorBuilderImpl.this.subscriptionDeactivatedHandler
      override val subscriptionUpcomingInvoiceHandler: EventHandler[SubscriptionUpcomingInvoice] = AppMarketConnectorBuilderImpl.this.upcomingInvoiceHandler
      override val credentialsSupplier: AppMarketCredentialsSupplier = AppMarketConnectorBuilderImpl.this.credentialsSupplier
    }
  }
}
