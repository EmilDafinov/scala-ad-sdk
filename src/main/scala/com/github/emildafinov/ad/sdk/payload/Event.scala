package com.github.emildafinov.ad.sdk.payload

import com.github.emildafinov.ad.sdk.payload.EventType.EventType
import com.github.emildafinov.ad.sdk.payload.NoticeType.NoticeType

case class Event(`type`: EventType,
                 marketplace: Marketplace,
                 creator: User,
                 payload: Payload)

case class Marketplace(partner: String, baseUrl: String)
case class User()
case class Payload(account: Option[Account],
                   notice: Option[Notice],
                   company: Company)

case class Account(parentAccountIdentifier: Option[String])

case class Notice(`type`: NoticeType)

case class Company()
