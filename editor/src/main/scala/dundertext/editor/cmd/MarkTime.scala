package dundertext.editor.cmd

import dundertext.editor.TimingNode

object MarkTime extends CommandDescription {
  def apply() = new MarkTime
}

class MarkTime extends SubtitlingCommand {
  override def execute(): Unit = {
    player.pause()
    val insertPos: TimingNode = buffer.findNodeAfter(player.currentTime)
    val t = player.currentTime
    val tn = TimingNode(t)
    buffer.insertBefore(tn, insertPos)
    buffer.relink()
    player.cue(cursor.text.display.out.minus(1))
  }
}
