package com.github.emildafinov.ad.sdk.event.responses.marshallers;

import com.github.emildafinov.ad.sdk.payload.ApiResult;

/**
 * Implementations of this interface serve to convert an event response to
 * @param <T>
 */
@FunctionalInterface
public interface EventResponseMarshaller<T> {
	ApiResult convertToAppMarketResponse(T eventResponse);
}
