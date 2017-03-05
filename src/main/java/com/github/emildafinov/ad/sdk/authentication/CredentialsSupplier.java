package com.github.emildafinov.ad.sdk.authentication;

import java.util.Optional;

public interface CredentialsSupplier {
	Optional<MarketplaceCredentials> readCredentialsFor(String clientKey);
}
