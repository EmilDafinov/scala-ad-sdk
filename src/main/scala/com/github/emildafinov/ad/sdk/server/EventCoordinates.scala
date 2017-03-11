package com.github.emildafinov.ad.sdk.server

/**
  * Contains the information necessary to retrieve an event's payload
  * @param clientId the id of the client to whom the event was sent
  * @param eventFetchUrl the url for retrieving the event payload
  */
case class EventCoordinates(clientId: String, eventFetchUrl: String)
