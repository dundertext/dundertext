package dundertext.server

import akka.actor.Actor
import dundertext.editor.DocumentPatch
import dundertext.server.store.FileStore

import scala.concurrent.Promise

class DocumentActor extends Actor with DocumentActorState {
  def receive = {
    case p: DocumentActor.Patch =>
      patch(p.patches)
      p.p.success("OK")
  }
}

trait DocumentActorState {
  private val doc = new MasterDocument
  private val store = new FileStore()

  protected def patch(patches: Seq[DocumentPatch]): Unit = {
    println (patches.mkString("\n"))
    doc.handle(patches)
    //store.put(doc.buffer.build().copy(id = "TEST"))
    store.log("TEST", patches)
    //println (doc.buffer)
  }
}

object DocumentActor {
  case class Patch(docId: String, patches: Seq[DocumentPatch], p: Promise[String] = Promise())
}
