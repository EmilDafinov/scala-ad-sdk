package com.github.emildafinov.ad.sdk.event

import com.github.emildafinov.ad.sdk.AkkaDependenciesModule
import com.github.emildafinov.ad.sdk.authentication.AuthorizationTokenGenerator
import com.github.emildafinov.ad.sdk.event.parsers.RichEventParsersModule
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionOrder
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse
import com.github.emildafinov.ad.sdk.internal.ClientDefinedDependenciesModule
import com.github.emildafinov.ad.sdk.server.EventResultMarshallersModule

trait RawEventHandlersModule {
  this: ClientDefinedDependenciesModule
    with AkkaDependenciesModule
    with RichEventParsersModule
    with EventResultMarshallersModule =>

  private lazy val authorizationTokenGenerator = new AuthorizationTokenGenerator()
  private implicit lazy val eventResolver = new AppMarketEventResolver(authorizationTokenGenerator, credentialsSupplier)
  lazy implicit val eventFetcher = new AppMarketEventFetcher(
    credentialsSupplier = credentialsSupplier,
    authorizationTokenGenerator = authorizationTokenGenerator
  )

  lazy val subscriptionOrderRawEventHandler: RawEventHandler[SubscriptionOrder, SubscriptionOrderResponse] =
    new RawEventHandler(
      transformToClientEvent = subscriptionOrderEventParser,
      clientEventHandler = subscriptionOrderHandler,
      toMarketplaceResponse = subscriptionOrderResponseMarshaller
    )

  lazy val subscriptionCancelRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionCancelEventParser,
      clientEventHandler = subscriptionCancelHandler,
      toMarketplaceResponse = subscriptionCancelResponseMarshaller
    )

  lazy val subscriptionChangedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionCancelEventParser,
      clientEventHandler = subscriptionCancelHandler,
      toMarketplaceResponse = subscriptionCancelResponseMarshaller
    )

  lazy val subscriptionDeactivatedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionDeactivatedEventParser,
      clientEventHandler = subscriptionDeactivatedHandler,
      toMarketplaceResponse = subscriptionDeactivatedResponseMarshaller
    )

  lazy val subscriptionReactivatedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionReactivatedEventParser,
      clientEventHandler = subscriptionReactivatedHandler,
      toMarketplaceResponse = subscriptionReactivatedResponseMarshaller
    )

  lazy val subscriptionClosedRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionClosedEventParser,
      clientEventHandler = subscriptionClosedHandler,
      toMarketplaceResponse = subscriptionClosedResponseMarshaller
    )

  lazy val subscriptionUpcomingInvoiceRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = subscriptionUpcomingInvoiceEventParser,
      clientEventHandler = subscriptionUpcomingInvoiceHandler,
      toMarketplaceResponse = subscriptionUpcomingInvoiceResponseMarshaller
    )

  lazy val userAssignmentRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = userAssignmentEventParser,
      clientEventHandler = userAssignedHandler,
      toMarketplaceResponse = userAssignmentResponseMarshaller
    )

  lazy val userUnassignmentRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = userUnassignmentEventParser,
      clientEventHandler = userUnassignedHandler,
      toMarketplaceResponse = userUnassignmentResponseMarshaller
    )
  
  lazy val addonSubscriptionOrderRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = addonSubscriptionOrderEventParser,
      clientEventHandler = addonSubscriptionOrderHandler,
      toMarketplaceResponse = addonSubscriptionOrderResponseMarshaller
    )
  
  lazy val addonSubscriptionCancelRawEventHandler =
    new RawEventHandler(
      transformToClientEvent = addonSubscriptionCancelEventParser,
      clientEventHandler = addonSubscriptionCancelHandler,
      toMarketplaceResponse = addonSubscriptionCancelResponseMarshaller
    )
}
