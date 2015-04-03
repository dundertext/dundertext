package dundertext.editor.cmd

object DeleteChar {

  object Left extends CommandDescription {
    def apply() = new Left
  }

  class Left extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtBeginningOfRow

    override def execute(): Unit = {
      cursor.row.delete(cursor.pos-1)
      cursor.moveLeft(1)
    }
  }
}
