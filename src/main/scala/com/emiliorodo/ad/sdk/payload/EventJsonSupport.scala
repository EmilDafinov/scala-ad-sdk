package com.emiliorodo.ad.sdk.payload

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.emiliorodo.ad.sdk.events.parsers.SubscriptionOrderEvent
import com.emiliorodo.ad.sdk.events.payloads.responses.SubscriptionOrderResponse
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val marketInfoFormat: RootJsonFormat[MarketInfo] = jsonFormat0(MarketInfo)
  implicit val userInfoFormat: RootJsonFormat[UserInfo] = jsonFormat0(UserInfo)
  implicit val eventFlagFormat: RootJsonFormat[EventFlag] = jsonFormat0(EventFlag)
  implicit val eventTypeFormat: RootJsonFormat[EventType] = jsonFormat0(EventType)
  implicit val subscriptionOrderFormat: RootJsonFormat[SubscriptionOrderEvent] = jsonFormat1(SubscriptionOrderEvent)
  implicit val subscriptionOrderResponseFormat: RootJsonFormat[SubscriptionOrderResponse] = jsonFormat0(SubscriptionResponseEvent)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat6(Event)
}
