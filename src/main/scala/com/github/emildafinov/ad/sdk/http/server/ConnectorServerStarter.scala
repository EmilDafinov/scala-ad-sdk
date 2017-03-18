package com.github.emildafinov.ad.sdk.http.server

import akka.http.scaladsl.Http
import com.github.emildafinov.ad.sdk.ConnectorRootApplicationContext
import com.typesafe.scalalogging.StrictLogging

import scala.util.{Failure, Success}

private[sdk] trait ConnectorServerStarter extends StrictLogging {
  this: ConnectorRootApplicationContext =>

  def start(): Unit = {
    val connectorHttpServerInterface: String = config.getString("http.server.interface")
    val connectorHttpServerPort: Int = config.getInt("http.server.port")
    val healthPort:Int = config.getInt("http.server.health.port")
    
    val httpExt = Http()
    
    httpExt.bindAndHandle(
        handler = baseRoute,
        interface = connectorHttpServerInterface,
        port = connectorHttpServerPort
      )
      .onComplete {
        case Success(serverBinding) =>
          logger.info(s"Http server started on $connectorHttpServerInterface:$connectorHttpServerPort")
          sys.addShutdownHook {
            serverBinding.unbind()
            logger.info("Http server stopped")
          }
        case Failure(exception) =>
          logger.error(s"Cannot start server on $connectorHttpServerInterface:$connectorHttpServerPort", exception)
          sys.addShutdownHook {
            logger.info("Http server stopped")
          }
      }
    
    httpExt.bindAndHandle(
      handler = health, 
      interface = connectorHttpServerInterface, 
      port = healthPort
    ).onComplete {
      case Success(serverBinding) =>
        logger.info(s"Http server for healthcheck route started on $connectorHttpServerInterface:$healthPort")
        sys.addShutdownHook {
          serverBinding.unbind()
          logger.info("Http server stopped")
        }
      case Failure(exception) =>
        logger.error(s"Cannot start healthcheck server on $connectorHttpServerInterface:$healthPort", exception)
        sys.addShutdownHook {
          logger.info("Http server stopped")
        }
    }
  }
}
