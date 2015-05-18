package dundertext.server

import java.util.LinkedList

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.editor._

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handle(patchS: String): Unit = {
    val patch = DocumentPatch.unserialize(patchS)
    patch match {
      case tp: TextPatch => diff(tp.old, tp.now)
      case atp: AddTextPatch =>
      case atmp: AddTimingPatch =>
    }
    patch.apply(buffer)
    println (buffer.asText)
  }

  def diff(source: String, target: String) = {
    val textPatch: LinkedList[Patch] = new DiffMatchPatch().patch_make(source, target)
    println(textPatch)
  }
}
