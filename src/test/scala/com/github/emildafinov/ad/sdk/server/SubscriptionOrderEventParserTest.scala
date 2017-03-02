package com.github.emildafinov.ad.sdk.server

import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.events.parsers.SubscriptionOrderEventParser
import com.github.emildafinov.ad.sdk.events.payloads.SubscriptionOrderEvent
import com.github.emildafinov.ad.sdk.payload.Event

class SubscriptionOrderEventParserTest extends UnitTestSpec {

  behavior of "SubscriptionOrderEventParser"

  val testedParser = SubscriptionOrderEventParser()

  it should "parse a SubscriptionOrder from the incoming event" in {
    //Given
    val expectedId = "expectedEventId"
    val testEvent = Event(id = expectedId, `type` = null, marketplace = null, applicationUuid = null, flag = null, creator = null)
    val expectedSubscriptionOrder = SubscriptionOrderEvent(id = expectedId)

    //When
    val parsedEvent = testedParser(testEvent)

    //Then
    parsedEvent shouldEqual expectedSubscriptionOrder
  }
}
