package com.github.emildafinov.ad.sdk.event;

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier;
import com.github.emildafinov.ad.sdk.event.resolvers.EventResolverFactory$;
import com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse;

/**
 * Used by SDK clients for accessing classes allowing to send back notifications about completed
 * events back to the monolith
 */
public interface EventResolverFactory {
	default EventResolverFactory get(AppMarketCredentialsSupplier credentialsSupplier) {
		return EventResolverFactory$.MODULE$.apply(credentialsSupplier);
	}
	EventResolver<SubscriptionOrderResponse> subscriptionOrderResolver(); 
}
