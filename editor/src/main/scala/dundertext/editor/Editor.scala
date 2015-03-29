package dundertext.editor

import dundertext.editor.cmd.SubtitlingCommand

class Editor {
  var buffer: DocumentBuffer = _
  var cursor: Cursor = new Cursor
  var player: Player = _

  def execute(cmd: SubtitlingCommand): Unit = {
    cmd.link(this, player)
    if (cmd.applies)
      cmd.execute()
  }

  def focusBeginning(): Unit = {
    cursor.moveTo(buffer.firstSubtitle)
  }

  def placeCursorAtVideo(): Unit = {
    val textNode = buffer.findNodeAt(player.currentTime)
    cursor.moveTo(textNode)
  }
}

object Editor {
  def apply(b: DocumentBuffer) = {
    val e = new Editor
    e.buffer = b
    //e.focusBeginning()
    e
  }
}
