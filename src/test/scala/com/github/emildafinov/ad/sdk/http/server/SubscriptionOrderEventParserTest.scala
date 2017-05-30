package com.github.emildafinov.ad.sdk.http.server


import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.event.unmarshallers.SubscriptionOrderEventParser
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionOrderEvent
import com.github.emildafinov.ad.sdk.payload.{Event, EventType, Marketplace, Payload}

class SubscriptionOrderEventParserTest extends UnitTestSpec {

  behavior of "SubscriptionOrderEventParser"

  val testedParser = SubscriptionOrderEventParser()

  it should "parse a SubscriptionOrder from the incoming event" in {
    //Given
    val expectedId = "expectedEventId"
    val testEvent = Event(
      `type` = EventType.SUBSCRIPTION_ORDER, 
      marketplace = Marketplace(
        partner = "TESTPARTNER",
        baseUrl = "www.example.com"
      ),
      payload = Payload()
    )
    val expectedSubscriptionOrder = SubscriptionOrderEvent(id = expectedId)

    //When
    val parsedEvent = testedParser(testEvent, expectedId)

    //Then
    parsedEvent shouldEqual expectedSubscriptionOrder
  }
}
