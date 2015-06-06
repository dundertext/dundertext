package dundertext.server

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model.{HttpRequest, StatusCodes, HttpResponse, Uri}

import scala.concurrent.Future

class HttpHandler(documentHandler: DocumentHandler) {
  val notFound = Future.successful(HttpResponse(StatusCodes.NotFound, entity = "Not found"))

  /** / */
  def handle(req: HttpRequest): Future[HttpResponse] = req.uri.path match {
    case Slash(Segment("api", Slash(tail))) => api(req, tail)
    case _ => notFound
  }

  /** /api/ */
  def api(req: HttpRequest, path: Uri.Path): Future[HttpResponse] = path match {
    case Segment("document", Slash(tail)) => document(req, tail)
    case _ => notFound
  }

  /** /api/document/ */
  def document(req: HttpRequest, path: Uri.Path): Future[HttpResponse] = path match {
    case Segment(docId, Empty) => documentHandler.handle(req, docId)
    case _ => notFound
  }
}
