package dundertext.editor.cmd

class BlurCursor extends SubtitlingCommand {
  override def execute(): Unit = {
    editor.cursor.blur()
  }
}
