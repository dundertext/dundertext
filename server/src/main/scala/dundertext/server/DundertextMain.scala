package dundertext.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Future

class DundertextMain {
  implicit val system = ActorSystem("Dundertext")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()
  val documentsActor: DocumentsActor.Ref = DocumentsActor.create()
  val documentHandler = new DocumentHandler(documentsActor)

  val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
    Http(system).bind(interface = "localhost", port = 8080)

  def httpHandler = new HttpHandler(documentHandler)

  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
    connection.handleWithAsyncHandler(httpHandler.handle)
  }).run()
}

object DundertextMain {
  def main(args: Array[String]): Unit = {
    println("Starting Dundertext")
    new DundertextMain
  }
}
