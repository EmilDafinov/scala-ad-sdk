package com.github.emildafinov.ad.sdk

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait AkkaSpec {
  implicit val system = ActorSystem("test-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
}
