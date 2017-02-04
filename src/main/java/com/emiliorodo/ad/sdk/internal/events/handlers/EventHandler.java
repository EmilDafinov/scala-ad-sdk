package com.emiliorodo.ad.sdk.internal.events.handlers;

public interface EventHandler<T, U> {
	U handle(T event);
}
