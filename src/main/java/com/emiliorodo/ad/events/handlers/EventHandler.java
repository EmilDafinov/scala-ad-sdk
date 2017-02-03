package com.emiliorodo.ad.events.handlers;

public interface EventHandler<T, U> {
	U handle(T event);
}
