package dundertext.editor.cmd

import dundertext.data.{Span, Row}
import dundertext.editor.RowNode

object AddRow extends CommandDescription {
  def apply() = new AddRow
}

class AddRow extends SubtitlingCommand {
  override def applies: Boolean = {
    cursor.row.hasText && cursor.isAtEndOfRow
  }

  override def execute(): Unit = {
    val newRow = RowNode.from(Row(List(Span(""))))
    cursor.text.insertRowAfter(cursor.row, newRow)
    cursor.moveTo(newRow)
  }
}
