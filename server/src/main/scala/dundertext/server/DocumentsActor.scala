package dundertext.server

import akka.actor._
import dundertext.editor.DocumentPatch
import dundertext.server.DocumentActor._

import scala.collection.mutable
import scala.concurrent.Future

class DocumentsActor extends Actor with DocumentsActorState {
  def receive = {
    case r: Patch => docActor(r.docId) ! r
    case Terminated(child)      => terminated(child)
  }

  protected def newWatchedChild(props: Props): ActorRef = {
    val ar = context.actorOf(Props(classOf[DocumentActor]))
    context.watch(ar)
    ar
  }
}

object DocumentsActor {
  case class Ref(a: ActorRef) {
    def patch(docId: String, patches: Seq[DocumentPatch]): Future[String] = {
      val req = Patch(docId, patches)
      a ! req
      req.p.future
    }
  }

  def create()(implicit f: ActorRefFactory)= Ref(f.actorOf(Props(new DocumentsActor)))
}

trait DocumentsActorState {
  private val documents = new mutable.HashMap[String, Entry]()

  private class Entry {
    var actorRef: ActorRef = _
    var state: State = _
  }

  protected def docActor(id: String): ActorRef = {
    documents.getOrElseUpdate(id, newDocument(id)).actorRef
  }

  private def newDocument(id: String): Entry = {
    val entry = new Entry
    entry.actorRef = newWatchedChild(Props(classOf[DocumentActor]))
    documents.put(id, entry)
    entry
  }

  protected def terminated(child: ActorRef): Unit = {

  }

  protected def newWatchedChild(props: Props): ActorRef
}
