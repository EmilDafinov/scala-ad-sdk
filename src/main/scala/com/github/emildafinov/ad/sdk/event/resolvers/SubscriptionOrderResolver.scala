package com.github.emildafinov.ad.sdk.event.resolvers

import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.authentication.CredentialsSupplier
import com.github.emildafinov.ad.sdk.event.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.ApiResults

class SubscriptionOrderResolver(credentialsSupplier: CredentialsSupplier,
                                appMarketEventResolver: AppMarketEventResolver) {
  
  def reresolveWithFailuresolve(errorMessage: String, eventReturnAddress: EventReturnAddress): Unit =
    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = ApiResults.unknownError(errorMessage)
    )
}
