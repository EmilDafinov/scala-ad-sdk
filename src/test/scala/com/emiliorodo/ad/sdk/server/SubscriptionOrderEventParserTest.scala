package com.emiliorodo.ad.sdk.server

import com.emiliorodo.ad.sdk.UnitTestSpec
import com.emiliorodo.ad.sdk.payload.{Event, SubscriptionOrderEvent}

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
