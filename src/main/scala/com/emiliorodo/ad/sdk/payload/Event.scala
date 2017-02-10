package com.emiliorodo.ad.sdk.payload

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.emiliorodo.ad.sdk.events.payloads.events.SubscriptionOrder
import com.emiliorodo.ad.sdk.events.payloads.responses.SubscriptionOrderResponse
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Event(id: String,
                 `type`: EventType,
                 marketplace: MarketInfo,
                 applicationUuid: String,
                 flag: EventFlag,
                 creator: UserInfo
)

object EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat6(Event)
  implicit val marketInfoFormat: RootJsonFormat[MarketInfo] = jsonFormat0(MarketInfo)
  implicit val userInfoFormat: RootJsonFormat[UserInfo] = jsonFormat0(UserInfo)
  implicit val eventFlagFormat: RootJsonFormat[EventFlag] = jsonFormat0(EventFlag)
  implicit val eventTypeFormat: RootJsonFormat[EventType] = jsonFormat0(EventType)
  implicit val subscriptionOrder: RootJsonFormat[SubscriptionOrderEvent] = jsonFormat1(SubscriptionOrderEvent)
  implicit val subscriptionOrderResponse: RootJsonFormat[SubscriptionOrderResponse] = jsonFormat0(SubscriptionResponseEvent)
  
}

case class MarketInfo()

case class EventFlag()

case class UserInfo()

case class EventType()

case class SubscriptionOrderEvent(id: String) extends SubscriptionOrder
case class SubscriptionResponseEvent() extends SubscriptionOrderResponse
