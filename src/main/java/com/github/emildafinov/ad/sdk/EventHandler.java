package com.github.emildafinov.ad.sdk;

@FunctionalInterface
public interface EventHandler<T, U> {
	U handle(T event);
}
