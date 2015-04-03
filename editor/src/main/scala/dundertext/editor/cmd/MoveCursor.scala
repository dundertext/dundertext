package dundertext.editor.cmd

object MoveCursor {

  object Left extends CommandDescription {
    def apply() = new Left
  }

  class Left extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtBeginningOfRow

    override def execute(): Unit = {
      cursor.moveLeft(1)
    }
  }

  object Right extends CommandDescription {
    def apply() = new Right
  }

  class Right extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtEndOfRow

    override def execute(): Unit = {
      cursor.moveRight(1)
    }
  }

  object Up extends CommandDescription {
    def apply() = new Up
  }

  class Up extends SubtitlingCommand {
    override def applies = cursor.row.prev != null

    override def execute(): Unit = {
      cursor.moveTo(cursor.row.prev)
    }
  }

  object Down extends CommandDescription {
    def apply() = new Down
  }

  class Down extends SubtitlingCommand {
    override def applies = cursor.row.next != null

    override def execute(): Unit = {
      cursor.moveTo(cursor.row.next)
    }
  }

  object RowBegin extends CommandDescription {
    def apply() = new RowBegin
  }

  class RowBegin extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtBeginningOfRow

    override def execute(): Unit = {
      cursor.moveTo(cursor.row)
    }
  }

  object RowEnd extends CommandDescription {
    def apply() = new RowEnd
  }

  class RowEnd extends SubtitlingCommand {
    override def applies: Boolean =
      !cursor.isAtEndOfRow

    override def execute(): Unit = {
      cursor.moveRowEnd()
    }
  }


}
