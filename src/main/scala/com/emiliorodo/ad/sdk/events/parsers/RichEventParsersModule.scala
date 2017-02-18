package com.emiliorodo.ad.sdk.events.parsers

import com.emiliorodo.ad.sdk.events.payloads.events._
import com.emiliorodo.ad.sdk.payload.Event

trait RichEventParsersModule {
  lazy val subscriptionOrderEventParser: Event => SubscriptionOrder = SubscriptionOrderEventParser()
  lazy val subscriptionCancelEventParser: Event => SubscriptionCancel = SubscriptionCancelEventParser()
  lazy val subscriptionChangeEventParser: Event => SubscriptionChange = SubscriptionChangeEventParser()
}

object SubscriptionOrderEventParser {
  def apply(): Event => SubscriptionOrder =
    event => SubscriptionOrderEvent(id = event.id)
}

object SubscriptionCancelEventParser {
  def apply(): Event => SubscriptionCancel = 
    event => SubscriptionCancelEvent(event.id)
}

object SubscriptionChangeEventParser {
  def apply(): Event => SubscriptionChange =
    event => SubscriptionChangeEvent(event.id)
}

object SubscriptionClosedEventParser {
  def apply(): Event => SubscriptionClosed =
    event => SubscriptionClosedEvent(event.id)
}

object SubscriptionDeactivatedEventParser {
  def apply(): Event => SubscriptionDeactivated =
    event => SubscriptionDeactivatedEvent(event.id)
}

object SubscriptionReactivatedEventParser {
  def apply(): Event => SubscriptionReactivated =
    event => SubscriptionReactivatedEvent(event.id)
}

object SubscriptionUpcomingInvoice {
  def apply(): Event => SubscriptionUpcomingInvoice =
    event => SubscriptionUpcomingInvoiceEvent(event.id)
}

case class SubscriptionChangeEvent(id: String) extends SubscriptionChange

case class SubscriptionClosedEvent(id: String) extends SubscriptionClosed

case class SubscriptionDeactivatedEvent(id: String) extends SubscriptionDeactivated

case class SubscriptionReactivatedEvent(id: String) extends SubscriptionReactivated

case class SubscriptionUpcomingInvoiceEvent(id: String) extends SubscriptionUpcomingInvoice

case class SubscriptionOrderEvent(id: String) extends SubscriptionOrder

case class SubscriptionCancelEvent(id: String) extends SubscriptionCancel
