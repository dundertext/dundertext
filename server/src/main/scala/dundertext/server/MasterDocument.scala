package dundertext.server

import java.util.LinkedList

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.editor._

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handle(patches: Seq[DocumentPatch]): Seq[DocumentPatch] = {
    for (p <- patches) yield handle(p)
  }

  private[server] def handle(patch: DocumentPatch): DocumentPatch = patch match {
    case p: TextPatch      => applyText(p)
    case p: AddTextPatch   => p.apply(buffer); p
    case p: AddTimingPatch => p.apply(buffer); p
  }

  private def applyText(p: TextPatch): DocumentPatch = {
    try {
      p.apply(buffer)
      p
    } catch { case e: TextPatchException =>
      println(e)
      val p2 = transform(p, e.current)
      p2.apply(buffer)
      p2
    }
  }

  private def transform(p: TextPatch, current: String): TextPatch = {
    val remote: LinkedList[Patch] = new DiffMatchPatch().patch_make(p.old, p.now)
    val Array(patched: String, applied: Array[Boolean]) = new DiffMatchPatch().patch_apply(remote, current)
    p.copy(old = current, now = patched)
  }
}
