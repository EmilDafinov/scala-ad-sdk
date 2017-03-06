package com.github.emildafinov.ad.sdk.internal

import javax.naming.OperationNotSupportedException

import com.github.emildafinov.ad.sdk.UnitTestSpec
import com.github.emildafinov.ad.sdk.payload.Event

class UnimplementedEventHandlerTest extends UnitTestSpec {

  behavior of "UnimplementedEventHandler"

  val testedHandler = UnimplementedEventHandler(classOf[Object])

  it should "throw when processing an event" in {
    //Given
    val testEvent = Event(null, null, null)

    //Then
    an [OperationNotSupportedException] shouldBe thrownBy {

      //When
      testedHandler handle testEvent
    }
  }
}
