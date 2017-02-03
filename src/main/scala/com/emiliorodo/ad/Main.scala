package com.emiliorodo.ad

import com.emiliorodo.ad.events.handlers.EventHandler
import com.emiliorodo.ad.events.payloads.events.{SubscriptionCancel, SubscriptionChange, SubscriptionOrder}
import com.emiliorodo.ad.events.payloads.responses.{SubscriptionCancelResponse, SubscriptionChangeResponse, SubscriptionOrderResponse}

object Main extends App {

  val isvSubOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse] = (event: SubscriptionOrder) => ???
  val isvSubChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = (event: SubscriptionChange) => ???
  val isvSubCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse] = (event: SubscriptionCancel) => ???

  val connector = new AppdirectConnectorBuilder(
    subscriptionOrderHandler = isvSubOrderHandler,
    subscriptionCancelHandler = isvSubCancelHandler
  )
  .subscriptionChangeHandler(isvSubChangeHandler)
  .build()
    
  connector.start()


}

