package dundertext.editor.cmd

import dundertext.editor.TextNode

object NewText extends CommandDescription {
  def apply() = new NewText
}

class NewText extends SubtitlingCommand {
  override def execute(): Unit = {
    val t = TextNode.empty
    buffer.append(t)
    buffer.relink()
    cursor.moveTo(t)
  }
}
