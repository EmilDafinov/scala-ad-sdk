package com.emiliorodo.ad

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

/**
  * @author edafinov
  */
trait AkkaDependenciesModule {
  implicit val system = ActorSystem("main")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
}
