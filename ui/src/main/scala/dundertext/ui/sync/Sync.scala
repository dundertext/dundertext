package dundertext.ui.sync

import dundertext.editor._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

class Sync {
  val syncedBuffer = DocumentBuffer.empty
  var editor: Editor = _

  def setEditor(editor: Editor): Unit = {
    this.editor = editor
  }

  // init
  dom.setInterval(timedSync _, 500)
  // end-init

  def timedSync(): Unit = {
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
  }

  def syncText(now: TextNode, oldO: Option[TextNode]): Unit = {
    oldO match {
      case Some(old)  => sync(TextPatch(now.id, old.text, now.text))
      case None       => sync(AddTextPatch(now.id, now.prev.id))
                         sync(TextPatch(now.id, "", now.text))
    }
  }

  def syncTime(now: TimingNode, oldO: Option[TimingNode]): Unit = {
    oldO match {
      case Some(old)  =>
      case None       => sync(AddTimingPatch(now.id, now.prev.id, now.time))
    }
  }

  def sync(patch: DocumentPatch): Unit = {
    Ajax.post("/api/document", patch.serialize)
  }
}
