package dundertext.server

import java.nio.file.{Files, Paths, StandardOpenOption}

import akka.http.scaladsl.model._
import akka.stream.FlowMaterializer
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration._

class CommandHandler(implicit val materializer: FlowMaterializer) {

  val d = Paths.get("work")
  d.toFile.mkdirs()
  val f = d.resolve("document.tsv")

  val doc = new MasterDocument

  def save(entity: RequestEntity): Unit = {
    val body: ByteString = Await.result(entity.toStrict(1.second), 1.second).data // TODO: NONONONONONONONO!!!
    Files.write(f, body.toArray, StandardOpenOption.CREATE, StandardOpenOption.APPEND)
    doc.handlePatches(body.utf8String)
  }

  def handle(req: HttpRequest): HttpResponse = req.method match {
    case HttpMethods.GET => HttpResponse(200, entity = "Goder afton")
    case HttpMethods.POST => save(req.entity); HttpResponse(200)
    case _ => HttpResponse(StatusCodes.MethodNotAllowed)
  }
}
