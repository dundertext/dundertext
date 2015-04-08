package dundertext.editor.cmd

object NoSpace extends CommandDescription {
  def apply() = new NoSpace
}

class NoSpace extends SubtitlingCommand {
  override def applies = {
    cursor.isAtText
  }

  override def execute(): Unit = {
  }
}
