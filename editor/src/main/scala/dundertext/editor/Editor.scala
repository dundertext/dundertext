package dundertext.editor

import dundertext.editor.cmd.SubtitlingCommand

class Editor {
  var buffer: DocumentBuffer = _
  var cursor: Cursor = new Cursor
  var player: Player = _

  def execute(cmd: SubtitlingCommand): Unit = {
    cmd.link(this, player)
    cmd.execute()
  }

  def focusBeginning(): Unit = {
    cursor.moveTo(buffer.firstSubtitle)
  }
}

object Editor {
  def apply(b: DocumentBuffer) = {
    val e = new Editor
    e.buffer = b
    e.focusBeginning()
    e
  }
}