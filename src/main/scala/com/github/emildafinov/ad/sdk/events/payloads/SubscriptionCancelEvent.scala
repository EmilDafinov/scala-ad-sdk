package com.github.emildafinov.ad.sdk.events.payloads

import com.github.emildafinov.ad.sdk.events.payloads.events.SubscriptionCancel

case class SubscriptionCancelEvent(id: String) extends SubscriptionCancel
