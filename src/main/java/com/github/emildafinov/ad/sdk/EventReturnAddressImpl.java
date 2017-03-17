package com.github.emildafinov.ad.sdk;

public class EventReturnAddressImpl implements EventReturnAddress {

	private final String eventId;
	private final String marketplaceBaseUrl;
	private final String clientId;

	public EventReturnAddressImpl(String eventId, String marketplaceBaseUrl, String clientId) {
		this.eventId = eventId;
		this.marketplaceBaseUrl = marketplaceBaseUrl;
		this.clientId = clientId;
	}

	public String eventId() {
		return eventId;
	}

	public String marketplaceBaseUrl() {
		return marketplaceBaseUrl;
	}

	public String clientId() {
		return clientId;
	}
}
