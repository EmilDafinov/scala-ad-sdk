package com.github.emildafinov.ad.sdk.event.payloads;

import com.github.emildafinov.ad.sdk.EventReturnAddress;

/**
 * Instances of this interface are used by the client event handling logic to send
 * a signal to the event sender that event processing has completed.
 * @param <T>
 */
public interface EventResolver<T> {
	void resolveWithFailure(String errorMessage, EventReturnAddress eventReturnAddress);
	void resolveSuccessfully(T eventResponse, EventReturnAddress eventReturnAddress);
}
