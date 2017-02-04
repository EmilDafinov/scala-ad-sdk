package com.emiliorodo.ad.sdk.service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}

class DummyService {

  def getAkkaWebPageAsString(implicit 
                             actorMaterializer: ActorMaterializer, 
                             actorSystem: ActorSystem, 
                             ec: ExecutionContext): Future[String] = 
    for {
      response <- Http().singleRequest(HttpRequest(uri = "http://akka.io"))
      responseBody <- response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
    } yield responseBody.decodeString("utf8")
}
