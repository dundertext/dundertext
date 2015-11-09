package dundertext.editor

import scala.collection.mutable.ListBuffer

abstract class Sync {
  val syncedBuffer = DocumentBuffer.empty
  var editor: Editor = _
  var active = ListBuffer[DocumentPatch]()

  def setEditor(editor: Editor): Unit = {
    this.editor = editor
  }

  def timedSync(): Unit = {
    if (active.nonEmpty) {
      println ("No sync, pending req")
      return
    }

    for (n: DocumentNode <- editor.buffer.entries) {
      if (!n.synced) {
        val oldO = syncedBuffer.findNodeById(n.id)
        n match {
          case txn: TextNode   => syncText(txn, oldO.asInstanceOf[Option[TextNode]])
          case tmn: TimingNode => syncTime(tmn, oldO.asInstanceOf[Option[TimingNode]])
        }
      }
      n.synced = true
    }

    if (active.nonEmpty)
      sendPatches(active.toList)
  }

  private def syncText(now: TextNode, oldO: Option[TextNode]): Unit = {
    oldO match {
      case Some(old)  => sync(TextPatch(now.id, old.text, now.text))
      case None       => sync(AddTextPatch(now.id, now.prev.id))
                         sync(TextPatch(now.id, "\n", now.text))
    }
  }

  private def syncTime(now: TimingNode, oldO: Option[TimingNode]): Unit = {
    oldO match {
      case Some(old)  =>
      case None       => sync(AddTimingPatch(now.id, now.prev.id, now.time))
    }
  }

  def sync(patch: DocumentPatch): Unit = {
    active += patch
  }

  def sendPatches(patches: List[DocumentPatch]): Unit

  def receivePatches(patches: Seq[DocumentPatch]): Unit = {
    for (p <- patches) p.apply(syncedBuffer)
    active.clear()
  }
}
