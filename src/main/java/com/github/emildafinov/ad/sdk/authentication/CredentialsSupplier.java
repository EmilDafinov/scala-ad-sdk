package com.github.emildafinov.ad.sdk.authentication;

import java.util.Optional;

/**
 * Allows the connector to
 */
public interface CredentialsSupplier {
	Optional<MarketplaceCredentials> readCredentialsFor(String clientKey);
}
