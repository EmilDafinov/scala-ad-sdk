package com.github.emildafinov.ad.sdk

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier

private[sdk] trait ClientDefinedCredentialsModule {

  protected val credentialsSupplier: AppMarketCredentialsSupplier
}


