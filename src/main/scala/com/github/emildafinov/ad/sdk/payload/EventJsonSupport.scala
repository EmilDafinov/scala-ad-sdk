package com.github.emildafinov.ad.sdk.payload

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.github.emildafinov.ad.sdk.event.payloads.SubscriptionOrderEvent
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val marketInfoFormat: RootJsonFormat[Marketplace] = jsonFormat2(Marketplace.apply)
  implicit val userInfoFormat: RootJsonFormat[User] = jsonFormat0(User.apply)
  implicit val eventFlagFormat: RootJsonFormat[EventFlag] = jsonFormat0(EventFlag.apply)
  implicit val eventTypeFormat: RootJsonFormat[EventType] = jsonFormat0(EventType.apply)
  implicit val subscriptionOrderFormat: RootJsonFormat[SubscriptionOrderEvent] = jsonFormat1(SubscriptionOrderEvent.apply)
  implicit val subscriptionOrderResponseFormat: RootJsonFormat[SubscriptionResponseEvent] = jsonFormat2(SubscriptionResponseEvent.apply)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat4(Event.apply)
  implicit val eventPayloadFormat: RootJsonFormat[Payload] = jsonFormat3(Payload.apply)
  implicit val companyFormat: RootJsonFormat[Company] = jsonFormat0(Company.apply)
  implicit val apiResultFormat: RootJsonFormat[ApiResult] = jsonFormat5(ApiResult.apply)
  implicit val accountFormat: RootJsonFormat[Account] = jsonFormat1(Account.apply)
  implicit val noticeFormat: RootJsonFormat[Notice] = jsonFormat1(Notice.apply)
  
}
