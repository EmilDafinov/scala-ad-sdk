package com.github.emildafinov.ad.sdk.internal

import javax.naming.OperationNotSupportedException

import com.github.emildafinov.ad.sdk.event.EventResolver
import com.github.emildafinov.ad.sdk.payload.Event
import com.github.emildafinov.ad.sdk.{EventReturnAddressImpl, UnimplementedEventHandler, UnitTestSpec}

class UnimplementedEventHandlerTest extends UnitTestSpec {

  behavior of "UnimplementedEventHandler"

  private val testedHandler = UnimplementedEventHandler(classOf[Object])

  private val mockResolver = mock[EventResolver[Nothing]]


  it should "throw when processing an event" in {
    //Given
    val testEvent = Event(null, null, null, null)
    val mockReturnAddress = new EventReturnAddressImpl(null, null, null)

    //Then
    an [OperationNotSupportedException] shouldBe thrownBy {

      //When
      testedHandler.handle(testEvent, mockReturnAddress)
    }
  }
}
