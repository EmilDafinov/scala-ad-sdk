package com.github.emildafinov.ad.sdk.server.routing.directives

import akka.http.scaladsl.server.Directive
import akka.http.scaladsl.server.Directives.parameter
import com.github.emildafinov.ad.sdk.authentication.MarketplaceCredentials
import com.github.emildafinov.ad.sdk.event.AppMarketEventFetcher
import com.github.emildafinov.ad.sdk.payload.Event
import com.github.emildafinov.ad.sdk.server.EventCoordinates

object SignedFetchDirective {

  /**
    * Directive that performs the signed fetch of the event and makes the payload
    * available tou the inner [[akka.http.scaladsl.server.Route]]s
    * @param eventFetcher an object used to perform the signed fetch of the raw event payload
    * @param clientCredentials the id of the client who sent the request
    * @return
    */
  def apply(eventFetcher: AppMarketEventFetcher, clientCredentials: MarketplaceCredentials): Directive[(String, Event)] =
    parameter("eventUrl") map { eventFetchUrl =>
      eventFetcher.fetchRawAppMarketEvent(EventCoordinates(clientCredentials.clientKey(), eventFetchUrl))
    }
}
