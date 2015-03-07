package dundertext.editor.cmd

class Space extends SubtitlingCommand {
  override def applies = {
    !cursor.span.text.endsWith(" ")
  }

  override def execute(): Unit = {
    cursor.row.insert(cursor.pos, " ")
    cursor.moveRight(1)
  }
}
