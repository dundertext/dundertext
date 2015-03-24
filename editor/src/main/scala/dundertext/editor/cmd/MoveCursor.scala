package dundertext.editor.cmd

object MoveCursor {

  class Left extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtBeginningOfRow

    override def execute(): Unit = {
      cursor.moveLeft(1)
    }
  }

  class RowBegin extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtBeginningOfRow

    override def execute(): Unit = {
      cursor.moveTo(cursor.row)
    }
  }

  class Right extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtEndOfRow

    override def execute(): Unit = {
      cursor.moveRight(1)
    }
  }

  class Up extends SubtitlingCommand {
    override def applies = cursor.row.prev != null

    override def execute(): Unit = {
      cursor.moveTo(cursor.row.prev)
    }
  }

  class Down extends SubtitlingCommand {
    override def applies = cursor.row.next != null

    override def execute(): Unit = {
      cursor.moveTo(cursor.row.next)
    }
  }
}
