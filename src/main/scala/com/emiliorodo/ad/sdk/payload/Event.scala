package com.emiliorodo.ad.sdk.payload

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class Event(`type`: EventType,
                 marketplace: MarketInfo,
                 applicationUuid: String,
                 flag: EventFlag,
                 creator: UserInfo
)

object EventJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat5(Event)
  implicit val marketInfoFormat: RootJsonFormat[MarketInfo] = jsonFormat0(MarketInfo)
  implicit val userInfoFormat: RootJsonFormat[UserInfo] = jsonFormat0(UserInfo)
  implicit val eventFlagFormat: RootJsonFormat[EventFlag] = jsonFormat0(EventFlag)
  implicit val eventTypeFormat: RootJsonFormat[EventType] = jsonFormat0(EventType)
}

case class MarketInfo()

case class EventFlag()

case class UserInfo()

case class EventType()
