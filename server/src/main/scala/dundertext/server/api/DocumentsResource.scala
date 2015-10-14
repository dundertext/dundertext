package dundertext.server.api

import java.time.Instant

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import dundertext.server.AppContext
import dundertext.server.api.DocumentsResource._

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}

/** /api/documents/ */
class DocumentsResource(req: HttpRequest, subPath: Uri.Path)(
    implicit val ctx: AppContext,
    implicit val materializer: ActorMaterializer,
    implicit val ec: ExecutionContext
) extends Resource {

  def route: Future[HttpResponse] = subPath match {
    case Segment(docId, tail) => new DocumentResource(req, docId, tail).route
    case Empty => handle
    case _ => notFound
  }

  def handle: Future[HttpResponse] = req.method match {
    case HttpMethods.GET  => get
    case HttpMethods.POST => post
    case _                => methodNotAllowed
  }

  def get = {
    def entity = HttpEntity.apply(ContentType(MediaTypes.`application/xml`),
      s"""<documents>
        |${documents.asScala.mkString("\n")}
        |</documents>
        |""".stripMargin)
    Future.successful(HttpResponse(StatusCodes.OK, entity = entity))
  }

  def post = {
    documents.add(Instant.now().toString)
    println (documents.asScala.mkString("\n"))
    Future.successful(HttpResponse(StatusCodes.Accepted))
  }
}

object DocumentsResource {
  val documents = new java.util.LinkedHashSet[String]()
}
