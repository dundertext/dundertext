package dundertext.server

import akka.actor.Actor
import dundertext.editor.DocumentPatch

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

  protected def patch(patches: Seq[DocumentPatch]): Unit = {
    patches foreach doc.handle
    println (doc.buffer)
  }
}

object DocumentActor {
  case class Patch(docId: String, patches: Seq[DocumentPatch], p: Promise[String] = Promise())
}
