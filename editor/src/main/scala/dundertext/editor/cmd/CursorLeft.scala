package dundertext.editor.cmd

class CursorLeft extends SubtitlingCommand {
  override def applies: Boolean =
    !cursor.isAtBeginningOfRow

  override def execute(): Unit = {
    cursor.moveLeft(1)
  }
}

class CursorRight extends SubtitlingCommand {
  override def execute(): Unit = {
    cursor.moveRight(1)
  }
}

class CursorUp extends SubtitlingCommand {
  override def applies = cursor.row.prev != null

  override def execute(): Unit = {
    cursor.moveTo(cursor.row.prev)
  }
}

class CursorDown extends SubtitlingCommand {
  override def applies = cursor.row.next != null

  override def execute(): Unit = {
    cursor.moveTo(cursor.row.next)
  }
}
