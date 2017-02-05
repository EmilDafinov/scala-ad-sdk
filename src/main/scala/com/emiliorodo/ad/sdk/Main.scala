package com.emiliorodo.ad.sdk

import com.emiliorodo.ad.sdk.events.payloads.events.{AddonSubscriptionCancel, SubscriptionCancel, SubscriptionChange, SubscriptionOrder}
import com.emiliorodo.ad.sdk.events.payloads.responses.{AddonSubscriptionCancelResponse, AddonSubscriptionOrderResponse, SubscriptionCancelResponse, SubscriptionOrderResponse}
import com.emiliorodo.ad.sdk.external.AppdirectConnectorBuilder
import com.emiliorodo.ad.sdk.internal.events.handlers.EventHandler
import com.emiliorodo.ad.sdk.internal.events.payloads.events._
import com.emiliorodo.ad.sdk.internal.events.payloads.responses._

object Main extends App {

  val isvSubOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse] = (event: SubscriptionOrder) => ???
  val isvSubChangeHandler: EventHandler[SubscriptionChange, SubscriptionChangeResponse] = (event: SubscriptionChange) => ???
  val isvSubCancelHandler: EventHandler[SubscriptionCancel, SubscriptionCancelResponse] = (event: SubscriptionCancel) => ???
  val isvAddonSubOrderHandler: EventHandler[AddonSubscriptionOrder, AddonSubscriptionOrderResponse] = (event: AddonSubscriptionOrder) => ???
  val isvAddonSubCancelHandler: EventHandler[AddonSubscriptionCancel, AddonSubscriptionCancelResponse] = (event: AddonSubscriptionCancel) => ???

  val connector = new AppdirectConnectorBuilder(
    subscriptionOrderHandler = isvSubOrderHandler,
    subscriptionCancelHandler = isvSubCancelHandler
  )
  .subscriptionChangeHandler(isvSubChangeHandler)
  .addonSubscriptionOrderHandler(isvAddonSubOrderHandler)
  .addonSubscriptionCancelHandler(isvAddonSubCancelHandler)
  .build()
    
  connector.start()
}