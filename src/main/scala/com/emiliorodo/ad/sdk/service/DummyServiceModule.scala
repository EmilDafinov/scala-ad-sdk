package com.emiliorodo.ad.sdk.service

import com.emiliorodo.ad.sdk.AkkaDependenciesModule

private[sdk] trait DummyServiceModule {
  this: AkkaDependenciesModule =>
  lazy val dummyService  = new DummyService
}
