package dundertext.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import dundertext.server.api.RootResource

import scala.concurrent.Future

class DundertextMain {
  implicit val system = ActorSystem("Dundertext")
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  implicit object context extends AppContext {
    val documentsActor: DocumentsActor.Ref = DocumentsActor.create()
  }

  def listenHttp(): Unit = {
    val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] =
      Http(system).bind(interface = "localhost", port = 8080)

    def handler(req: HttpRequest): Future[HttpResponse] = {
      new RootResource(req).route
    }

    val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
      connection.handleWithAsyncHandler(handler)
    }).run()
  }

  listenHttp()
}

object DundertextMain {
  def main(args: Array[String]): Unit = {
    println("Starting Dundertext")
    new DundertextMain
  }
}
