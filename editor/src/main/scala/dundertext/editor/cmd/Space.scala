package dundertext.editor.cmd

class Space extends SubtitlingCommand {
  override def applies = {
    !charLeftOfCursorIsSpace && !cursor.isAtBeginningOfRow
  }

  private def charLeftOfCursorIsSpace: Boolean = {
    if (cursor.isAtBeginningOfRow) false
    else cursor.row.text.charAt(cursor.pos - 1) == ' '
  }

  private def charRightOfCursorIsSpace: Boolean = {
    if (cursor.isAtEndOfRow) false
    else cursor.row.text.charAt(cursor.pos) == ' '
  }

  override def execute(): Unit = {
    if (!charRightOfCursorIsSpace)
      cursor.row.insert(cursor.pos, " ")

    cursor.moveRight(1)
  }
}
