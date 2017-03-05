package com.github.emildafinov.ad.sdk.payload

case class Event(`type`: String,
                 marketplace: MarketInfo,
                 creator: UserInfo
)
