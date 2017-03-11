package com.github.emildafinov.ad.sdk.server

import akka.http.scaladsl.server.{Directive, Directives}
import com.github.emildafinov.ad.sdk.event.AppMarketEventFetcher
import com.github.emildafinov.ad.sdk.payload.Event

trait CustomDirectivesModule extends Directives {

  def signedFetchEvent(clientId: String, eventFetcher: AppMarketEventFetcher): Directive[(String, Event)] = 
    parameter("eventUrl") tmap { eventFetchUrl =>
      eventFetcher.fetchRawAppMarketEvent(EventCoordinates(clientId, eventFetchUrl._1))
    }
}
