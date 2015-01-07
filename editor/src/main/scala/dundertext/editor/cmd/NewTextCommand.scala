package dundertext.editor.cmd

import dundertext.editor.TextNode

class NewTextCommand extends SubtitlingCommand {
  override def execute(): Unit = {
    val t = new TextNode()
    t.append("")
    buffer.append(t)
    buffer.relink()
    cursor.moveTo(t)
  }
}
