package com.github.emildafinov.ad.sdk.payload

case class Event(`type`: String,
                 marketplace: MarketInfo,
                 creator: UserInfo,
                 payload: EventPayload)

case class EventPayload(account: Account)

case class Account(parentAccountIdentifier: String)
