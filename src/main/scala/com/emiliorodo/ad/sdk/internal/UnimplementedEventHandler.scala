package com.emiliorodo.ad.sdk.internal

import javax.naming.OperationNotSupportedException

import com.emiliorodo.ad.sdk.events.handlers.EventHandler

private[sdk] class UnimplementedEventHandler[U, T](eventType: Class[U]) extends EventHandler[U, T] {
  
  override def handle(event: U): T = 
     throw new OperationNotSupportedException(s"Events of type ${eventType.getCanonicalName} are not supported.")
}

private[sdk] object UnimplementedEventHandler {
  def apply[U, T](eventType: Class[U]): UnimplementedEventHandler[U, T] = new UnimplementedEventHandler(eventType)
}
