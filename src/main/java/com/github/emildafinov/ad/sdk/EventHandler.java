package com.github.emildafinov.ad.sdk;

import com.github.emildafinov.ad.sdk.event.payloads.EventResolver;

@FunctionalInterface
public interface EventHandler<T, S> {
	void handle(T event, EventResolver<S> eventResolver);
}
