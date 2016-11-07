package dundertext.editor

import scala.collection.mutable

abstract class Sync {
  var editor: Editor = _

  var idCounter: Int = 0

  val ownChanges = mutable.Buffer[DocumentPatch]()

  def setEditor(editor: Editor): Unit = {
    this.editor = editor
  }

  def newSyncId(): Int = {
    idCounter += 1
    idCounter
  }

  def sync(): Unit = {
    val syncId = "editor"+newSyncId()
    for (n: DocumentNode <- editor.buffer.entries) {
      n match {
        case txn: TextNode   => syncText(txn, syncId)
        case tmn: TimingNode => syncTime(tmn, syncId)
      }
    }

    if (ownChanges.nonEmpty) {
      sendPatches(ownChanges.toList)
      ownChanges.clear()
    }
  }

  private def syncText(txn: TextNode, syncId: String): Unit = {
    val (old, now) = txn.sync()
    if (old == null) {
      sync(TextPatch(syncId, txn.id, txn.prev.id, "\n", now.text))
      txn.pendingSyncId = syncId
    } else if (old != now) {
      sync(TextPatch(syncId, txn.id, txn.prev.id, old.text, now.text))
      txn.pendingSyncId = syncId
    }
  }

  private def syncTime(tmn: TimingNode, syncId: String): Unit = {
    val (old, now) = tmn.sync()
    if ((old == null) || old != now) {
      sync(TimingPatch(tmn.id, tmn.prev.id, tmn.time))
    }
  }

  private def sync(patch: DocumentPatch): Unit = {
    ownChanges += patch
  }

  def sendPatches(patches: List[DocumentPatch]): Unit

  def receivePatches(patches: Seq[DocumentPatch]): Unit = {
    sync()
    for (p <- patches) {
      apply(p, editor.buffer)
    }
  }

  private def apply(p: DocumentPatch, b: DocumentBuffer): Unit = p match {
    case p: TextPatch   => applyText(p, b)
    case p: TimingPatch => applyTiming(p, b)
    case p: RemovePatch => applyRemoval(p, b)
  }

  private def applyText(p: TextPatch, b: DocumentBuffer): Unit = {
    val tn: TextNode = b.getOrCreateTextNode(p.id, p.prevId)
    if (tn.pendingSyncId == null || tn.pendingSyncId == p.syncId) {
      tn.pendingSyncId = null
      tn.rows.clear()
      for (rs <- p.target.split('\n'))
        tn.rows += RowNode.from(rs)
      tn.relink()
    }
  }

  private def applyTiming(p: TimingPatch, b: DocumentBuffer): Unit = {
    val n = b.getOrCreateTiming(p.id, p.prevId)
    n.time = p.t
  }

  private def applyRemoval(p: RemovePatch, b: DocumentBuffer): Unit = {
    b.deleteNode(p.id)
  }
}
