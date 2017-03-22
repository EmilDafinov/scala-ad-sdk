package com.github.emildafinov.ad.sdk.internal

import com.github.emildafinov.ad.sdk.{EventHandler, EventReturnAddress}
import com.typesafe.scalalogging.StrictLogging

private[sdk] class UnimplementedEventHandler[U](eventType: Class[U]) extends EventHandler[U] with StrictLogging {
  
  override def handle(event: U, eventReturnAddress: EventReturnAddress): Unit =
    logger.error(s"Events of type ${eventType.getCanonicalName} are not supported.")
}

private[sdk] object UnimplementedEventHandler {
  def apply[U, T](eventType: Class[U]): UnimplementedEventHandler[U] = new UnimplementedEventHandler(eventType)
}
