package dundertext.editor.cmd

object BlurOnEmptyLast extends CommandDescription {
  def apply() = new BlurOnEmptyLast
}

class BlurOnEmptyLast extends SubtitlingCommand {
  override def applies: Boolean = {
    cursor.row.isEmpty && cursor.row.isLast
  }

  override def execute() = {
    cursor.row.remove()
    cursor.blur()
  }
}
