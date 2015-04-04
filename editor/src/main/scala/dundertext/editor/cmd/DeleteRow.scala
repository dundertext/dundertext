package dundertext.editor.cmd

import dundertext.editor.RowNode

object DeleteRow extends CommandDescription {
  def apply() = new DeleteRow
}

class DeleteRow extends SubtitlingCommand {
  override def applies: Boolean = {
    (cursor.text.rowCount > 1) &&
    (cursor.row.prev != null)
  }

  override def execute() = {
    val source: RowNode = cursor.row
    val target: RowNode = cursor.row.prev
    cursor.moveTo(target)
    cursor.moveRowEnd()
    source.remove()
  }
}
