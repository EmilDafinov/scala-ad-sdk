package com.github.emildafinov.ad.sdk.payload

case class Event(`type`: String,
                 marketplace: Marketplace,
                 creator: User,
                 payload: Payload)

case class Marketplace(partner: String, baseUrl: String)
case class User()
case class Payload(account: Option[Account] = None,
                   notice: Option[Notice]= None,
                   company: Company)

case class Account(parentAccountIdentifier: Option[String] = None)

case class Notice(`type`: String)

case class Company()
