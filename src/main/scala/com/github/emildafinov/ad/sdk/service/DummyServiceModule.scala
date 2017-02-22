package com.github.emildafinov.ad.sdk.service

import com.github.emildafinov.ad.sdk.AkkaDependenciesModule

private[sdk] trait DummyServiceModule {
  this: AkkaDependenciesModule =>
  lazy val dummyService  = new DummyService
}
