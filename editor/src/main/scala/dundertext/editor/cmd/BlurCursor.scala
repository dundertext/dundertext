package dundertext.editor.cmd

object BlurCursor extends CommandDescription {
  def apply() = new BlurCursor
}

class BlurCursor extends SubtitlingCommand {
  override def execute(): Unit = {
    editor.cursor.blur()
  }
}
