package com.github.emildafinov.ad.sdk.internal

import com.github.emildafinov.ad.sdk.authentication.AppMarketCredentialsSupplier

private[sdk] trait ClientDefinedCredentialsModule {

  val credentialsSupplier: AppMarketCredentialsSupplier
}
