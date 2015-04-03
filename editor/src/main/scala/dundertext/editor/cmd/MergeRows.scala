package dundertext.editor.cmd

import dundertext.editor.RowNode

object MergeRows extends CommandDescription {
  def apply() = new MergeRows
}

class MergeRows extends SubtitlingCommand {
  override def applies: Boolean = {
    cursor.text.rowCount > 1
    cursor.row.prev != null
  }

  override def execute() = {
    val source: RowNode = cursor.row
    val target: RowNode = cursor.row.prev
    cursor.moveTo(target)
    cursor.moveRowEnd()
    target.append(source.asSpans())
    source.remove()
  }
}
