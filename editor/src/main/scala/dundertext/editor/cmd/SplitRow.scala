package dundertext.editor.cmd

import dundertext.data.Row
import dundertext.editor.RowNode

object SplitRow extends CommandDescription {
  def apply() = new SplitRow
}

class SplitRow extends SubtitlingCommand {
  override def applies: Boolean = {
    cursor.row.hasText
  }

  override def execute(): Unit = {
    val old: Row = cursor.row.build()
    val (r1, r2) = old.split(cursor.pos)

    cursor.row.set(r1)
    cursor.row.trim()
    val newRow = RowNode.from(r2)
    cursor.text.insertRowAfter(cursor.row, newRow)
    cursor.moveTo(newRow)
  }
}
