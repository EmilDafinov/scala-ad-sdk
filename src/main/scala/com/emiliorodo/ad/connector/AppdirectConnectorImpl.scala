package com.emiliorodo.ad.connector

import akka.http.scaladsl.Http
import com.emiliorodo.ad.events.handlers.EventHandler
import com.emiliorodo.ad.events.payloads.events.SubscriptionOrder
import com.emiliorodo.ad.events.payloads.responses.SubscriptionOrderResponse
import com.emiliorodo.ad.{AppdirectConnector, ConnectorRootApplicationContext}
import com.typesafe.scalalogging.StrictLogging

import scala.util.{Failure, Success}

class AppdirectConnectorImpl(subscriptionOrderHandler: EventHandler[SubscriptionOrder, SubscriptionOrderResponse])
  extends ConnectorRootApplicationContext
  with StrictLogging
  with AppdirectConnector {
  
  override def start(): Unit = {
    startHttpServer()
  }
  
  private def startHttpServer(): Unit = {
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
