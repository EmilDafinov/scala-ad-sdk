package com.github.emildafinov.ad.sdk

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

private[sdk] trait AkkaDependenciesModule {
  protected implicit val system: ActorSystem
  protected implicit val materializer: ActorMaterializer
  protected implicit val executionContext: ExecutionContext
}

private[sdk] trait ConnectorServerAkkaDependenciesModule extends AkkaDependenciesModule {
  protected override implicit val system: ActorSystem = ActorSystem("server")
  protected override implicit val materializer: ActorMaterializer = ActorMaterializer()
  protected override implicit val executionContext: ExecutionContext = system.dispatcher
}

private[sdk] trait ConnectorResolversAkkaDependenciesModule extends AkkaDependenciesModule {
  protected override implicit val system: ActorSystem = ActorSystem("resolvers")
  protected override implicit val materializer: ActorMaterializer = ActorMaterializer()
  protected override implicit val executionContext: ExecutionContext = system.dispatcher
}
