package dundertext.server

import java.util.LinkedList

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.editor._

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handle(patch: DocumentPatch): Unit = patch match {
    case p: TextPatch      => applyText(p)
    case p: AddTextPatch   => p.apply(buffer)
    case p: AddTimingPatch => p.apply(buffer)
  }

  def applyText(p: TextPatch): Unit = {
    try {
      p.apply(buffer) }
    catch { case e: TextPatchException =>
      transform(p, e.current).apply(buffer)
    }
  }

  def transform(p: TextPatch, current: String): TextPatch = {
    val remote: LinkedList[Patch] = new DiffMatchPatch().patch_make(p.old, p.now)
    val Array(patched: String, applied: Array[Boolean]) = new DiffMatchPatch().patch_apply(remote, current)
    p.copy(old = current, now = patched)
  }
}
