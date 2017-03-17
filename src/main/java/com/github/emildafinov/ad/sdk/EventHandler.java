package com.github.emildafinov.ad.sdk;

@FunctionalInterface
public interface EventHandler<T> {
	void handle(T event, EventReturnAddress eventReturnAddress);
}
