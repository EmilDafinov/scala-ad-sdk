package com.github.emildafinov.ad.sdk.authentication;

/**
 * Defines a client/secret pair shared by every connector using the SDK and the AppMarket.
 * This information is used to sign all messages between the connector and the AppMarket.
 */
public interface AppMarketCredentials {
	String clientKey();
	String clientSecret();
}
