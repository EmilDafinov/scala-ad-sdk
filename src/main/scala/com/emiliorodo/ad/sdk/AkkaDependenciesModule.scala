package com.emiliorodo.ad.sdk

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

/**
  * @author edafinov
  */
private[sdk] trait AkkaDependenciesModule {
  implicit val system = ActorSystem("main")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
}
