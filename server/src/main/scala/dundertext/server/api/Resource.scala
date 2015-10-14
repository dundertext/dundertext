package dundertext.server.api

import akka.http.scaladsl.model.{StatusCodes, HttpResponse}

import scala.concurrent.Future

abstract class Resource {
  val notFound = Future.successful(HttpResponse(StatusCodes.NotFound, entity = "Not found"))
  val methodNotAllowed = Future.successful(HttpResponse(StatusCodes.MethodNotAllowed, entity = "Method not allowed"))
}
