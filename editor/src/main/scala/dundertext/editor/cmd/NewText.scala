package dundertext.editor.cmd

import dundertext.editor.TextNode

object NewText extends CommandDescription {
  def apply() = new NewText
}

class NewText extends SubtitlingCommand {
  override def execute(): Unit = {
    val t = TextNode.empty.withId(editor.newId())
    buffer.append(t)
    cursor.moveTo(t)
  }
}
