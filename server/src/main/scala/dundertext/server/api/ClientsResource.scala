package dundertext.server.api

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import dundertext.server.AppContext

import scala.concurrent.{ExecutionContext, Future}

/** /api/clients/ */
class ClientsResource(req: HttpRequest, subPath: Uri.Path)(
    implicit val ctx: AppContext,
    implicit val materializer: ActorMaterializer,
    implicit val ec: ExecutionContext
) extends Resource {

  def route: Future[HttpResponse] = subPath match {
    case Empty => handle
    case _ => notFound
  }

  def handle: Future[HttpResponse] = req.method match {
    case HttpMethods.GET  => get
    case HttpMethods.POST => post
    case _                => methodNotAllowed
  }

  def get = {
    def entity = HttpEntity.apply(ContentTypes.`text/xml(UTF-8)`,
      s"""<clients>
        |Här borde man se klienterna
        |</clients>
        |""".stripMargin)
    Future.successful(HttpResponse(StatusCodes.OK, entity = entity))
  }

  def post = {
    val userAgent: String = req.header[headers.`User-Agent`].mkString
    println("Client started using " + userAgent)
    Future.successful(HttpResponse(StatusCodes.OK))
  }
}
