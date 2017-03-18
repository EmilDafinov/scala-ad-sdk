package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.UserAssignment
import com.github.emildafinov.ad.sdk.payload.Event

object UserAssignmentEventParser {
  def apply(): (Event, String) => UserAssignment = ???
}
