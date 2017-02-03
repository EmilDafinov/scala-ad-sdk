package com.emiliorodo.ad

import com.emiliorodo.ad.connector.AppdirectConnectorImpl
import com.emiliorodo.ad.events.handlers.EventHandler
import com.emiliorodo.ad.events.payloads.events.{SubscriptionCancel, SubscriptionChange, SubscriptionOrder}
import com.emiliorodo.ad.events.payloads.responses.{SubscriptionCancelResponse, SubscriptionChangeResponse, SubscriptionOrderResponse}

class AppdirectConnectorBuilder(subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse],
                                subscriptionCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse]) {
  private var subscriptionChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = new UnimplementedEventHandler(classOf[SubscriptionChange])

  def subscriptionChangeHandler(eventHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse]): AppdirectConnectorBuilder = {
    subscriptionChangeHandler = eventHandler
    this
  }

  def build(): AppdirectConnector = {
    validateDependencies()
    new AppdirectConnectorImpl(subscriptionOrderHandler = subscriptionOrderHandler)
  }
  
  private def validateDependencies(): Unit = {
    require(
      subscriptionOrderHandler != null, 
      "Handling the SubscriptionOrder event is compulsory. Please provide the connector with an instance of " +
      "EventHandler<SubscriptionOrder, SubscriptionOrderResponse>"
    )
  }
}
