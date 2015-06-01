package dundertext.server

import java.util.LinkedList

import com.sksamuel.diffpatch.DiffMatchPatch
import com.sksamuel.diffpatch.DiffMatchPatch.Patch
import dundertext.editor._

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handlePatches(s: String): Unit = {
    println ("Handle")
    s.split('\u001e') foreach handle
  }

  def handle(patchS: String): Unit = {
    println("-------------------------------------")
    println (patchS)
    println("=====================================")

    val patch = DocumentPatch.unserialize(patchS)
    patch match {
      case tp: TextPatch => diff(tp.old, tp.now)
      case atp: AddTextPatch =>
      case atmp: AddTimingPatch =>
    }
    //patch.apply(buffer)
  }

  def diff(source: String, target: String) = {
    val textPatch: LinkedList[Patch] = new DiffMatchPatch().patch_make(source, target)
  }
}
