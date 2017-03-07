package com.github.emildafinov.ad.sdk.authentication;

public interface CredentialsSupplier {
	MarketplaceCredentials readCredentialsFor(String clientKey);
}
