package com.emiliorodo.ad.sdk.internal

import com.emiliorodo.ad.sdk.UnitTestSpec
import com.emiliorodo.ad.sdk.payload.Event

class UnimplementedEventHandlerTest extends UnitTestSpec {

  behavior of "UnimplementedEventHandler"
  
  val testedHandler = new UnimplementedEventHandler(classOf[Object])
  
  it should "throw" in {
    //Given
    val testEvent = Event(null, null, null, null, null, null)
    
    //Then
    an [Exception] shouldBe thrownBy {
      
      //When
      testedHandler handle testEvent
    }
  }
}
