package dundertext.editor.cmd

import dundertext.editor.TimingNode

object PlayCurrent extends CommandDescription {
  def apply() = new PlayCurrent
}

class PlayCurrent extends SubtitlingCommand {
  override def execute(): Unit = {
    if (cursor.isAtText) {
      player.cue(cursor.text.display.in)
      player.playUntil(cursor.text.display.out)
    } else {
      val next: TimingNode = buffer.findNodeAfter(player.currentTime)
      val prev: TimingNode = next.prevTime
      player.cue(prev.time)
      player.playUntil(next.time)
    }
  }
}
