package com.github.emildafinov.ad.sdk.payload

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionOrderEvent
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val marketInfoFormat: RootJsonFormat[MarketInfo] = jsonFormat2(MarketInfo)
  implicit val userInfoFormat: RootJsonFormat[UserInfo] = jsonFormat0(UserInfo)
  implicit val eventFlagFormat: RootJsonFormat[EventFlag] = jsonFormat0(EventFlag)
  implicit val eventTypeFormat: RootJsonFormat[EventType] = jsonFormat0(EventType)
  implicit val subscriptionOrderFormat: RootJsonFormat[SubscriptionOrderEvent] = jsonFormat1(SubscriptionOrderEvent)
  implicit val subscriptionOrderResponseFormat: RootJsonFormat[SubscriptionResponseEvent] = jsonFormat2(SubscriptionResponseEvent)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat3(Event)
  implicit val apiResultFormat: RootJsonFormat[ApiResult] = jsonFormat5(ApiResult)

}
