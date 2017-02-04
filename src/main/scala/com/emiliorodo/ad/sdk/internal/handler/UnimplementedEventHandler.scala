package com.emiliorodo.ad.sdk.internal.handler

import javax.naming.OperationNotSupportedException

import com.emiliorodo.ad.sdk.internal.events.handlers.EventHandler

class UnimplementedEventHandler[U, T](eventType: Class[U]) extends EventHandler[U, T] {
  
  override def handle(event: U): T = 
     throw new OperationNotSupportedException(s"Events of type ${eventType.getCanonicalName} are not supported.")
}
