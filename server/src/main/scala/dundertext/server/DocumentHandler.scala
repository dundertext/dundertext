package dundertext.server

import akka.http.scaladsl.model._
import akka.stream.FlowMaterializer
import dundertext.editor.DocumentPatch
import dundertext.server.Timeout.TO

import scala.concurrent.{ExecutionContext, Future}

class DocumentHandler(documentsActor: DocumentsActor.Ref)(
    implicit val materializer: FlowMaterializer,
    implicit val ec: ExecutionContext
) {

  /** /api/document/{docId} */
  def handle(req: HttpRequest, docId: String): Future[HttpResponse] = {
    def handlePatches(body: String): Future[String] = {
      val patches: Array[DocumentPatch] = body.split('\n').map(DocumentPatch.unserialize)
      documentsActor.patch(docId, patches)
    }

    req.method match {
      case HttpMethods.PATCH =>
        for {
          data   <- req.entity.toStrict(TO)
          result <- handlePatches(data.data.utf8String)
        } yield HttpResponse(StatusCodes.OK, entity = result)

      case _ =>
        Future.successful(HttpResponse(StatusCodes.MethodNotAllowed))
    }
  }
}
