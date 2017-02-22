package com.github.emildafinov.ad.sdk.payload

case class Event(id: String,
                 `type`: EventType,
                 marketplace: MarketInfo,
                 applicationUuid: String,
                 flag: EventFlag,
                 creator: UserInfo
)
