package com.github.emildafinov.ad.sdk.event.payloads;

import java.util.concurrent.CompletableFuture;

import com.github.emildafinov.ad.sdk.EventReturnAddress;
import com.github.emildafinov.ad.sdk.event.SdkProvidedEventResolver;
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver;
import com.github.emildafinov.ad.sdk.payload.ApiResult;
import scala.Function1;

/**
 * Implementations of this class is what connectors implemented using the SDK would use in order to signal 
 * to the AppMarket that the processing of an event has been completed
 * @param <T> The type of event completed response that is being sent back in case of success. 
 *           Do note that it has to correspond to the type of event being handled: for example a {@link SubscriptionOrder}
 *           event should use a {@link EventResolver<com.github.emildafinov.ad.sdk.event.responses.SubscriptionOrderResponse>},
 *           or else the results might be unpredictable.
 */
public interface EventResolver<T> {
	CompletableFuture<Void> resolveWithFailure(String errorMessage, EventReturnAddress eventReturnAddress);
	CompletableFuture<Void> resolveSuccessfully(T eventResponse, EventReturnAddress eventReturnAddress);
}
