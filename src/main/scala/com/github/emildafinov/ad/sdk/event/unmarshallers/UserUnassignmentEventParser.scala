package com.github.emildafinov.ad.sdk.event.unmarshallers

import com.github.emildafinov.ad.sdk.event.payloads.UserUnassignment
import com.github.emildafinov.ad.sdk.payload.Event

object UserUnassignmentEventParser {
  def apply(): (Event, String) => UserUnassignment = ???  
}
