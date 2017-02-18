package com.emiliorodo.ad.sdk.events.handlers;

public interface EventHandler<T, U> {
	U handle(T event);
}
