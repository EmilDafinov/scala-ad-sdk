package com.github.emildafinov.ad.sdk.event.payloads;

/**
 * Instances of this interface are used by the client event handling logic to send
 * a signal to the event sender that event processing has completed.
 * @param <T>
 */
public interface EventResolver<T> {
	void resolveWithFailure(String errorMessage);
	void resolveSuccessfully(T eventResponse);
}
