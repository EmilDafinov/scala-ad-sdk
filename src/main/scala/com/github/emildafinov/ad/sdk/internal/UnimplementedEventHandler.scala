package com.github.emildafinov.ad.sdk.internal

import javax.naming.OperationNotSupportedException

import com.github.emildafinov.ad.sdk.{EventHandler, EventReturnAddress}

private[sdk] class UnimplementedEventHandler[U](eventType: Class[U]) extends EventHandler[U] {
  
  override def handle(event: U, eventReturnAddress: EventReturnAddress): Unit = 
     throw new OperationNotSupportedException(s"Events of type ${eventType.getCanonicalName} are not supported.")
}

private[sdk] object UnimplementedEventHandler {
  def apply[U, T](eventType: Class[U]): UnimplementedEventHandler[U] = new UnimplementedEventHandler(eventType)
}
