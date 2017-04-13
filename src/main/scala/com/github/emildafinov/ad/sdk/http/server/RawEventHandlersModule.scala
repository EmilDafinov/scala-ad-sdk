package com.github.emildafinov.ad.sdk.http.server

import com.github.emildafinov.ad.sdk.{AkkaDependenciesModule, ClientDefinedDependenciesModule}
import com.github.emildafinov.ad.sdk.event.RoutingDependenciesModule
import com.github.emildafinov.ad.sdk.event.marshallers.EventResultMarshallersModule
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionOrder
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse
import com.github.emildafinov.ad.sdk.event.unmarshallers.RichEventParsersModule

trait RawEventHandlersModule {
  this: ClientDefinedDependenciesModule
    with AkkaDependenciesModule
    with RichEventParsersModule
    with RoutingDependenciesModule
    with EventResultMarshallersModule =>

  lazy val subscriptionOrderRawEventHandler: RawEventHandler[SubscriptionOrder, SubscriptionOrderResponse] =
    new RawEventHandler(
      transformToClientEvent = subscriptionOrderEventParser,
      clientEventHandler = subscriptionOrderHandler
    )

  lazy val subscriptionCancelRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionCancelEventParser,
      clientEventHandler = subscriptionCancelHandler
    )

  lazy val subscriptionChangedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionChangeEventParser,
      clientEventHandler = subscriptionChangeHandler
    )

  lazy val subscriptionDeactivatedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionDeactivatedEventParser,
      clientEventHandler = subscriptionDeactivatedHandler
    )

  lazy val subscriptionReactivatedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionReactivatedEventParser,
      clientEventHandler = subscriptionReactivatedHandler
    )

  lazy val subscriptionClosedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionClosedEventParser,
      clientEventHandler = subscriptionClosedHandler
    )

  lazy val subscriptionUpcomingInvoiceRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionUpcomingInvoiceEventParser,
      clientEventHandler = subscriptionUpcomingInvoiceHandler
    )

  lazy val userAssignmentRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = userAssignmentEventParser,
      clientEventHandler = userAssignedHandler
    )

  lazy val userUnassignmentRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = userUnassignmentEventParser,
      clientEventHandler = userUnassignedHandler
    )

  lazy val addonSubscriptionOrderRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = addonSubscriptionOrderEventParser,
      clientEventHandler = addonSubscriptionOrderHandler
    )

  lazy val addonSubscriptionCancelRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = addonSubscriptionCancelEventParser,
      clientEventHandler = addonSubscriptionCancelHandler
    )
}
