package dundertext.editor.cmd

import dundertext.editor.TimingNode

object MarkTime extends CommandDescription {
  def apply() = new MarkTime
}

class MarkTime extends SubtitlingCommand {
  override def execute(): Unit = {
    val insertPos: TimingNode = buffer.findNodeAfter(player.currentTime)
    val tn = TimingNode(player.currentTime)
    buffer.insertBefore(tn, insertPos)
    buffer.relink()
  }
}
