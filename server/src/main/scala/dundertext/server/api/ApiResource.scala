package dundertext.server.api

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import dundertext.server.AppContext

import scala.concurrent.{ExecutionContext, Future}

/** /api/ */
class ApiResource(req: HttpRequest, subPath: Uri.Path)(
    implicit val ctx: AppContext,
    implicit val materializer: ActorMaterializer,
    implicit val ec: ExecutionContext
) extends Resource {

  def route: Future[HttpResponse] = subPath match {
    case Segment("document", Slash(tail)) => new DocumentsResource(req, tail).route
    case Empty => handle
    case _ => notFound
  }

  def handle: Future[HttpResponse] = req.method match {
    case HttpMethods.GET => get
    case _               => methodNotAllowed
  }

  def get = {
    def entity = HttpEntity.apply(ContentType(MediaTypes.`application/xml`),
      """<app>
        |  <name>Dundertext API</name>
        |</app>
        |""".stripMargin)
    Future.successful(HttpResponse(StatusCodes.OK, entity = entity))
  }
}
