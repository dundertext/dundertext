package dundertext.server

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.data._
import dundertext.editor._

import scala.collection.mutable

class SyncedDocument {
  val entries: mutable.Buffer[Entry] = mutable.Buffer(Document.empty.entries:_*)

  def isEmpty: Boolean =
    entries.length == 2

  def getOrCreateText(id: String, prevId: String): (Int, Text) = {
    val idx: Int = entries.indexWhere(_.id == id)
    if (idx > 0) {
      (idx, entries(idx).asInstanceOf[Text])
    } else {
      val prevIdx = entries.indexWhere(_.id == prevId)
      val newEntry = Text(id, List(Row.of("")))
      entries.insert(prevIdx+1, newEntry)
      (prevIdx+1, newEntry)
    }
  }

  def getOrCreateTiming(id: String, prevId: String): (Int, Timing) = {
    val idx: Int = entries.indexWhere(_.id == id)
    if (idx > 0) {
      (idx, entries(idx).asInstanceOf[Timing])
    } else {
      val prevIdx = entries.indexWhere(_.id == prevId)
      val newEntry = Timing(id, Time.Start)
      entries.insert(prevIdx+1, newEntry)
      (prevIdx+1, newEntry)
    }
  }

  def remove(id: String): Unit = {
    val idx: Int = entries.indexWhere(_.id == id)
    if (idx > 0) {
      entries.remove(idx)
    }
  }

  def build = Document("", entries.toList, Nil)
}

class MasterDocument {

  val document = new SyncedDocument()

  def handle(patches: Seq[DocumentPatch]): Seq[DocumentPatch] = {
    for (p <- patches) yield handle(p)
  }

  private[server] def handle(patch: DocumentPatch): DocumentPatch = patch match {
    case p: TimingPatch => applyTiming(p)
    case p: TextPatch   => applyText(p)
    case p: RemovePatch => applyRemove(p)
  }

  private def textPatch(current: String, source: String, target: String): String = {
    val remote: java.util.LinkedList[Patch] = new DiffMatchPatch().patch_make(source, target)
    val Array(patched: String, applied: Array[Boolean]) = new DiffMatchPatch().patch_apply(remote, current)
    patched
  }

  private def applyText(p: TextPatch): DocumentPatch = {
    val (idx: Int, old: Text) = document.getOrCreateText(p.id, p.prevId)
    val patched = if (old.text == p.source) p.target else textPatch(old.text, p.source, p.target)
    val modified: Text = old.copy(rows = patched.split("\n").map(Row.of).toList)
    document.entries.update(idx, modified)
    p.copy(source = null, target = patched)
  }

  private def applyTiming(p: TimingPatch): DocumentPatch = {
    val (idx: Int, old: Timing) = document.getOrCreateTiming(p.id, p.prevId)
    val modified: Timing = old.copy(value = p.t)
    document.entries.update(idx, modified)
    p
  }

  private def applyRemove(p: RemovePatch): DocumentPatch = {
    document.remove(p.id)
    p
  }
}
