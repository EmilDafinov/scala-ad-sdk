package com.github.emildafinov.ad.sdk.event.marshallers

import com.github.emildafinov.ad.sdk.event.responses._
import com.github.emildafinov.ad.sdk.payload.ApiResult

trait EventResultMarshallersModule {
  lazy val subscriptionOrderResponseMarshaller: SubscriptionOrderResponse => ApiResult = ???

  lazy val subscriptionCancelResponseMarshaller: SubscriptionCancelResponse => ApiResult = ???

  lazy val subscriptionChangeResponseMarshaller: SubscriptionChangeResponse => ApiResult = ???

  lazy val subscriptionClosedResponseMarshaller: SubscriptionClosedResponse => ApiResult = ???

  lazy val subscriptionDeactivatedResponseMarshaller: SubscriptionDeactivatedResponse => ApiResult = ???

  lazy val subscriptionReactivatedResponseMarshaller: SubscriptionReactivatedResponse => ApiResult = ???

  lazy val subscriptionUpcomingInvoiceResponseMarshaller: SubscriptionUpcomingInvoiceResponse => ApiResult = ???
  
  lazy val userAssignmentResponseMarshaller: UserAssignmentResponse => ApiResult = ???
  
  lazy val userUnassignmentResponseMarshaller: UserUnassignmentResponse => ApiResult = ???
  
  lazy val addonSubscriptionOrderResponseMarshaller: AddonSubscriptionOrderResponse => ApiResult = ???
  
  lazy val addonSubscriptionCancelResponseMarshaller: AddonSubscriptionCancelResponse => ApiResult = ???
}
