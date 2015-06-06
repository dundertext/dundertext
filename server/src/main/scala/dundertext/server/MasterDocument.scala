package dundertext.server

import java.util.LinkedList

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.editor._

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handle(patch: DocumentPatch): Unit = patch match {
    case p: TextPatch      => diff(p.old, p.now)
                              p.apply(buffer)
    case p: AddTextPatch   => p.apply(buffer)
    case p: AddTimingPatch => p.apply(buffer)
  }

  def diff(source: String, target: String) = {
    val textPatch: LinkedList[Patch] = new DiffMatchPatch().patch_make(source, target)
  }
}
