package com.emiliorodo.ad

import akka.http.scaladsl.Http
import com.emiliorodo.ad.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.service.DummyServiceModule
import com.emiliorodo.ad.server._
import com.typesafe.scalalogging.StrictLogging

import scala.util.{Failure, Success}

/**
  * @author edafinov
  */
trait ApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RoutesModule

object Main extends App 
  with StrictLogging
  with ApplicationContext {

  val serverInterface = config.getString("http.server.interface")
  val serverPort = config.getInt("http.server.port")

  Http()
    .bindAndHandle(
      handler = baseRoute,
      interface = serverInterface,
      port = serverPort
    )
    .onComplete {
      case Success(serverBinding) =>
        logger.info(s"Server started on $serverInterface:$serverPort")
        sys.addShutdownHook {
          serverBinding.unbind()
          logger.info("Server stopped")
        }
      case Failure(exception) =>
        logger.error(s"Cannot start server on $serverInterface:$serverPort", exception)
        sys.addShutdownHook {
          logger.info("Server stopped")
        }
    }
}
