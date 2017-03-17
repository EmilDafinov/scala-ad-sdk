package com.github.emildafinov.ad.sdk.authentication;

import java.util.Optional;

/**
 * To be implemented in connectors using the SDK. It allows the connector implementors to define their
 * AppMarket credentials resolution logic, so that the SDK code can use it for communicating with the AppMarket
 */
public interface AppMarketCredentialsSupplier {
	Optional<AppMarketCredentials> readCredentialsFor(String clientKey);
}
