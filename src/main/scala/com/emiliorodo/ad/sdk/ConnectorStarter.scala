package com.emiliorodo.ad.sdk

import akka.http.scaladsl.Http
import com.typesafe.scalalogging.StrictLogging

import scala.util.{Failure, Success}

trait ConnectorStarter extends AppdirectConnector with StrictLogging {
  this: ConnectorRootApplicationContext =>

  def start(): Unit = {
    val connectorHttpServerInterface: String = config.getString("http.server.interface")
    val connectorHttpServerPort: Int = config.getInt("http.server.port")

    Http()
      .bindAndHandle(
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
  }
}
