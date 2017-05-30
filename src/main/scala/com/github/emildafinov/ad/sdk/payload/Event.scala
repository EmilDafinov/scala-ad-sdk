package com.github.emildafinov.ad.sdk.payload

import com.github.emildafinov.ad.sdk.payload.AccountStatus.AccountStatus
import com.github.emildafinov.ad.sdk.payload.EventFlag.EventFlag
import com.github.emildafinov.ad.sdk.payload.EventType.EventType
import com.github.emildafinov.ad.sdk.payload.NoticeType.NoticeType
import com.github.emildafinov.ad.sdk.payload.PricingDuration.PricingDuration
import com.github.emildafinov.ad.sdk.payload.PricingUnit.PricingUnit

case class Event(`type`: EventType,
                 marketplace: Marketplace,
                 applicationUuid: Option[String] = None,
                 creator: Option[User] = None,
                 flag: Option[EventFlag] = None,
                 payload: Payload)

object EventType extends Enumeration {
  type EventType = Value
  val SUBSCRIPTION_ORDER,
      SUBSCRIPTION_CANCEL,
      SUBSCRIPTION_CHANGE,
      SUBSCRIPTION_NOTICE,
      USER_ASSIGNMENT,
      USER_UNASSIGNMENT = Value
}

case class Marketplace(partner: String, 
                       baseUrl: String)

object EventFlag extends Enumeration {
  type EventFlag = Value
  val STATELESS, DEVELOPMENT = Value
}

case class User(uuid: String, 
                openId: String, 
                email: String, 
                firstName: String, 
                lastName: String, 
                language: String, 
                locale: String, 
                attributes: Map[String, String])

case class Payload(account: Option[Account] = None,
                   notice: Option[Notice] = None,
                   company: Option[Company] = None,
                   user: Option[User] = None,
                   order: Option[Order] = None,
                   configuration: Map[String, String] = Map.empty)


case class Account(accountIdentifier: String, 
                   status: AccountStatus, 
                   parentAccountIdentifier: Option[String] = None)

object AccountStatus extends Enumeration {
  type AccountStatus = Value
  val INITIALIZED, 
      FAILED, 
      FREE_TRIAL, 
      FREE_TRIAL_EXPIRED, 
      ACTIVE, 
      SUSPENDED, 
      CANCELLED = Value
}

case class Notice(`type`: NoticeType, 
                  message: Option[String] = None)

object NoticeType extends Enumeration {
  type NoticeType = Value
  val CLOSED,
  DEACTIVATED,
  REACTIVATED,
  UPCOMING_INVOICE = Value
}

case class Company(uuid: String, 
                   name: String, 
                   email: Option[String] = None, 
                   phoneNumber: Option[String] = None, 
                   website: String, 
                   country: String)

case class Order(editionCode: String, 
                 addonOfferingCode: Option[String] = None, 
                 pricingDuration: PricingDuration, 
                 items: List[OrderItem] = List.empty)

object PricingDuration extends Enumeration {
  type PricingDuration = Value
  val ONE_TIME,
      MONTHLY,
      QUARTERLY,
      SIX_MONTHS,
      YEARLY,
      TWO_YEARS,
      THREE_YEARS,
      DAILY = Value
}

object PricingUnit extends Enumeration {
  type PricingUnit = Value
  val USER,
      GIGABYTE,
      MEGABYTE,
      PROPERTY,
      UNIT,
      ROOM,
      HOST,
      PROVIDER,
      MANAGER,
      NOT_APPLICABLE = Value
}

case class OrderItem(unit: PricingUnit, 
                     quantity: Int)


