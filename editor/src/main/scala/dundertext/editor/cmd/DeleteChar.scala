package dundertext.editor.cmd

class DeleteChar extends SubtitlingCommand {

  override def execute(): Unit = {
    cursor.row.delete(cursor.pos-1)
    cursor.moveLeft(1)
  }
}
