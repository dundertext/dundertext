package dundertext.editor.cmd

class TypeTextCommand(text: String) extends SubtitlingCommand {

  override def execute(): Unit = {
    cursor.row.insert(cursor.pos, text)
  }
}
