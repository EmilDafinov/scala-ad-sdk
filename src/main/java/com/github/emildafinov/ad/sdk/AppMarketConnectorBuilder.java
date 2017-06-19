package com.github.emildafinov.ad.sdk;

import com.github.emildafinov.ad.sdk.event.payloads.AddonSubscriptionCancel;
import com.github.emildafinov.ad.sdk.event.payloads.AddonSubscriptionOrder;
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionChange;
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionClosed;
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionDeactivated;
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionReactivated;
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionUpcomingInvoice;
import com.github.emildafinov.ad.sdk.event.payloads.UserAssignment;
import com.github.emildafinov.ad.sdk.event.payloads.UserUnassignment;

public interface AppMarketConnectorBuilder {

	AppMarketConnectorBuilder subscriptionChangeHandler(EventHandler<SubscriptionChange> eventHandler);
	AppMarketConnectorBuilder addonSubscriptionCancelHandler(EventHandler<AddonSubscriptionCancel> eventHandler);
	AppMarketConnectorBuilder addonSubscriptionOrderHandler(EventHandler<AddonSubscriptionOrder> eventHandler);
	AppMarketConnectorBuilder userAssignedHandler(EventHandler<UserAssignment> eventHandler);
	AppMarketConnectorBuilder userUnassignedHandler(EventHandler<UserUnassignment> eventHandler);
	AppMarketConnectorBuilder subscriptionClosedHandler(EventHandler<SubscriptionClosed> eventHandler);
	AppMarketConnectorBuilder subscriptionDeactivatedHandler(EventHandler<SubscriptionDeactivated> eventHandler);
	AppMarketConnectorBuilder subscriptionReactivatedHandler(EventHandler<SubscriptionReactivated> eventHandler);
	AppMarketConnectorBuilder upcomingInvoiceHandler(EventHandler<SubscriptionUpcomingInvoice> eventHandler);
	AppMarketConnector build();
}
