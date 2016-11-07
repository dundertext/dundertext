package dundertext.server.api

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import dundertext.server.AppContext

import scala.concurrent.{ExecutionContext, Future}

/** / */
class RootResource(req: HttpRequest)(
    implicit val ctx: AppContext,
    implicit val materializer: ActorMaterializer,
    implicit val ec: ExecutionContext
) extends Resource {

  def route: Future[HttpResponse] = req.uri.path match {
    case Slash(Segment("api", Slash(tail))) => new ApiResource(req, tail).route
    case Slash(Empty) => handle
    case _ => notFound
  }

  def handle: Future[HttpResponse] = req.method match {
    case HttpMethods.GET => get
    case _               => methodNotAllowed
  }

  def get = {
    def entity = HttpEntity.apply(ContentTypes.`text/xml(UTF-8)`, """<app><name>Dundertext</name></app>""")
    Future.successful(HttpResponse(StatusCodes.OK, entity = entity))
  }
}
