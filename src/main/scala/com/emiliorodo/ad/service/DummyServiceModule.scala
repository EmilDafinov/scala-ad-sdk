package com.emiliorodo.ad.service

import com.emiliorodo.ad.AkkaDependenciesModule

trait DummyServiceModule {
  this: AkkaDependenciesModule =>
  lazy val dummyService  = new DummyService
}
