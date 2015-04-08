package dundertext.ui.video

import dundertext.editor.cmd.{CommandDescription, SubtitlingCommand}

object PlayCurrent extends CommandDescription {
  def apply() = new PlayCurrent
}

class PlayCurrent extends SubtitlingCommand  {
  override def execute(): Unit = {
    player.cue(cursor.text.display.in)
    player.playUntil(cursor.text.display.out)
  }
}
