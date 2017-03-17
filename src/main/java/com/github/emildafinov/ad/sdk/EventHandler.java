package com.github.emildafinov.ad.sdk;

/**
 * SDK clients would use this interface to define the connector-specific logic that has to be
 * executed upon receiving an event of a given type. For all event types that your connector 
 * supports, please pass the corresponding {@link EventHandler} implementation to the
 * {@link com.github.emildafinov.ad.sdk.external.AppMarketConnectorBuilder}
 * @param <T> The type of event that this {@link EventHandler} instance is handling.
 */
@FunctionalInterface
public interface EventHandler<T> {
	void handle(T event, EventReturnAddress eventReturnAddress);
}
