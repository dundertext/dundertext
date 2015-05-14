package dundertext.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Future

class DundertextMain {

  implicit val system = ActorSystem("Dundertext")
  implicit val materializer = ActorFlowMaterializer()

  val commandHandler = new CommandHandler

  val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
    Http(system).bind(interface = "localhost", port = 8080)

  def handler(req: HttpRequest): HttpResponse = {
    req.uri match {
      case Uri.Path("/api/document") => commandHandler.handle(req)
      case _ => HttpResponse(404, entity = "Not found")
    }
  }

  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
    connection.handleWithSyncHandler(handler)
  }).run()
}

object DundertextMain {
  def main(args: Array[String]): Unit = {
    println("Starting Dundertext")
    new DundertextMain
  }
}
