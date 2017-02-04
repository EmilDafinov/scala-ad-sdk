package com.emiliorodo.ad.sdk.internal.service

import com.emiliorodo.ad.sdk.internal.AkkaDependenciesModule

trait DummyServiceModule {
  this: AkkaDependenciesModule =>
  lazy val dummyService  = new DummyService
}
