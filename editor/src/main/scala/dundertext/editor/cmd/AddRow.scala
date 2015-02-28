package dundertext.editor.cmd

import dundertext.data.{Span, Row}
import dundertext.editor.RowNode

class AddRow extends SubtitlingCommand {
  override def execute(): Unit = {
    val newRow = RowNode.from(Row(List(Span(""))))
    cursor.text.insertRow(cursor.row, newRow)
    cursor.moveTo(newRow)
  }
}
