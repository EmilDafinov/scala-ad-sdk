package com.github.emildafinov.ad.sdk.event.marshallers

import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse
import com.github.emildafinov.ad.sdk.payload.ApiResult

object SubscriptionOrderResponseMarshaller {
  def apply() =
    (eventResponse: SubscriptionOrderResponse) => ApiResult(
      message = "Order placed successfully", 
      accountIdentifier = eventResponse.accountIdentifier(),
      userIdentifier = eventResponse.userIdentifier()
    )
}
