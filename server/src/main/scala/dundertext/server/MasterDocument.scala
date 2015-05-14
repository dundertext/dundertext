package dundertext.server

import dundertext.editor.{DocumentPatch, DocumentBuffer}

class MasterDocument {

  val buffer = DocumentBuffer.empty

  def handle(patchS: String): Unit = {
    def patch = DocumentPatch.unserialize(patchS)
    patch.apply(buffer)
    println (buffer.asText)
  }
}
