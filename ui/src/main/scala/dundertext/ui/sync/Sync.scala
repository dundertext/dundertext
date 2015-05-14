package dundertext.ui.sync

import dundertext.editor.{DocumentPatch, Editor}
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

class Sync {
  var editor: Editor = _

  // init
  dom.setInterval(timedSync _, 500)
  // end-init

  def timedSync(): Unit = {
    editor.log foreach sync
    editor.log.clear()
  }

  def setEditor(editor: Editor): Unit = {
    this.editor = editor
  }

  def sync(patch: DocumentPatch): Unit = {
    Ajax.post("/api/document", patch.serialize)
  }
}
