package com.github.emildafinov.ad.sdk.event

import java.util.concurrent.CompletableFuture

import com.github.emildafinov.ad.sdk.EventReturnAddress
import com.github.emildafinov.ad.sdk.event.payloads.EventResolver
import com.github.emildafinov.ad.sdk.event.responses.marshallers.EventResponseMarshaller
import com.github.emildafinov.ad.sdk.http.client.AppMarketEventResolver
import com.github.emildafinov.ad.sdk.payload.{ApiResult, ApiResults}
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}


class SdkProvidedEventResolver[T](appMarketEventResolver: AppMarketEventResolver,
                                  toAppMarketResponse: EventResponseMarshaller[T]) extends EventResolver[T] with StrictLogging {

  override def resolveWithFailure(errorMessage: String, eventReturnAddress: EventReturnAddress): CompletableFuture[Void] = {

    val javaFuture = new CompletableFuture[Void]()

    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = ApiResults.unknownError(errorMessage)
    ) onComplete complete(javaFuture)

    javaFuture
  }

  override def resolveSuccessfully(eventResult: T, eventReturnAddress: EventReturnAddress): CompletableFuture[Void] = {
    val javaFuture = new CompletableFuture[Void]()

    appMarketEventResolver.sendEventResolvedCallback(
      eventReturnAddress,
      eventProcessingResult = toAppMarketResponse.convertToAppMarketResponse(eventResult)
    ) onComplete complete(javaFuture)

    javaFuture
  }

  private def complete(future: CompletableFuture[Void]): Try[Unit] => Unit = {
    case Success(_) => future.complete(null)
    case Failure(exception) => future.completeExceptionally(exception)
  }
}
