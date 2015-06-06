package dundertext.ui.sync

import dundertext.editor._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

import scala.concurrent.Future

class Sync {
  val syncedBuffer = DocumentBuffer.empty
  var editor: Editor = _
  val patchQueue = new StringBuilder
  var activeRequest: Future[XMLHttpRequest] = _

  def setEditor(editor: Editor): Unit = {
    this.editor = editor
  }

  // init
  dom.setInterval(timedSync _, 500)
  // end-init

  def timedSync(): Unit = {
    if (activeRequest != null) {
      println ("No sync, pending req")
      return
    }

    for (n: DocumentNode <- editor.buffer.entries) {
      if (!n.synced) {
        val oldO = syncedBuffer.findNodeById(n.id)
        n match {
          case txn: TextNode => syncText(txn, oldO.asInstanceOf[Option[TextNode]])
          case tmn: TimingNode => syncTime(tmn, oldO.asInstanceOf[Option[TimingNode]])
        }
      }
      n.synced = true
    }
    finishSync()
  }

  def syncText(now: TextNode, oldO: Option[TextNode]): Unit = {
    val US = '\u001f'
    oldO match {
      case Some(old)  => sync(TextPatch(now.id, old.text(US), now.text(US)))
      case None       => sync(AddTextPatch(now.id, now.prev.id))
                         sync(TextPatch(now.id, "", now.text(US)))
    }
  }

  def syncTime(now: TimingNode, oldO: Option[TimingNode]): Unit = {
    oldO match {
      case Some(old)  =>
      case None       => sync(AddTimingPatch(now.id, now.prev.id, now.time))
    }
  }

  def sync(patch: DocumentPatch): Unit = {
    println(patch.serialize)
    println("----------------------------")
    patchQueue.append(patch.serialize).append("\n")
  }

  def finishSync(): Unit = {
    if (patchQueue.nonEmpty) {
      println("POST")
      activeRequest = Ajax.apply("PATCH", "/api/document/ABC", patchQueue.result(), 0, Map.empty, false, "")
      activeRequest.onComplete { _ => activeRequest = null }
      patchQueue.clear()
    }
  }
}
