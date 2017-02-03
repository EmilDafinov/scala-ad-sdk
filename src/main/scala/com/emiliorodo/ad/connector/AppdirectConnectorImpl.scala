package com.emiliorodo.ad.connector

import akka.http.scaladsl.Http
import com.emiliorodo.ad.{AppdirectConnector, ConnectorRootApplicationContext}
import com.typesafe.scalalogging.StrictLogging

import scala.util.{Failure, Success}

class AppdirectConnectorImpl
  extends ConnectorRootApplicationContext
  with StrictLogging
  with AppdirectConnector {


  override def start(): Unit = {

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
}
