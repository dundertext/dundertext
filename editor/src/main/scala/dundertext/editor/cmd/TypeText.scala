package dundertext.editor.cmd

class TypeText(text: String) extends SubtitlingCommand {

  override def execute(): Unit = {
    cursor.row.insert(cursor.pos, text)
    cursor.moveRight(text.length)
  }
}