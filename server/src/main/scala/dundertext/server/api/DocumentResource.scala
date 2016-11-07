package dundertext.server.api

import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import dundertext.editor.DocumentPatch
import dundertext.server.{Timeout, AppContext}

import scala.concurrent.{ExecutionContext, Future}

/** /api/documents/:docId */
class DocumentResource(req: HttpRequest, docId: String, subPath: Uri.Path)(
    implicit val ctx: AppContext,
    implicit val materializer: ActorMaterializer,
    implicit val ec: ExecutionContext
) extends Resource {

  def route: Future[HttpResponse] = subPath match {
    case Empty => handle
    case _ => notFound
  }

  def handle: Future[HttpResponse] = req.method match {
    case HttpMethods.GET => get
    case HttpMethods.PATCH => patch
    case _               => methodNotAllowed
  }

  def get = {
    def entity = HttpEntity.apply(ContentTypes.`text/xml(UTF-8)`,
      s"""<document>
         |  $docId
         |</document>
         |""".stripMargin)
    Future.successful(HttpResponse(StatusCodes.OK, entity = entity))
  }

  def patch: Future[HttpResponse] = {
    def handlePatches(body: String): Future[String] = {
      val patches: Array[DocumentPatch] = body.split('\n').map(DocumentPatch.unserialize)
      ctx.documentsActor.patch(docId, patches)
    }
    for {
      data   <- req.entity.toStrict(Timeout.TO)
      result <- handlePatches(data.data.utf8String)
    } yield HttpResponse(StatusCodes.OK, entity = result)
  }
}
