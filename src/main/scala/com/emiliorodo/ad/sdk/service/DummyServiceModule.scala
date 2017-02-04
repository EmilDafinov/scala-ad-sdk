package com.emiliorodo.ad.sdk.service

import com.emiliorodo.ad.sdk.AkkaDependenciesModule

trait DummyServiceModule {
  this: AkkaDependenciesModule =>
  lazy val dummyService  = new DummyService
}
