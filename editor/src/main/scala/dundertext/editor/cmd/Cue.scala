package dundertext.editor.cmd

import dundertext.editor.{TextNode, TimingNode}

object Cue extends CommandDescription {
  def apply() = new Cue
}

class Cue extends SubtitlingCommand {
  override def execute() = {
    val current = cursor.text.prevTime
    val prevTime: TimingNode = current.prevTime
    editor.player.cue(prevTime.time)
    val text: TextNode = prevTime.nextText
    cursor.moveTo(text)
  }
}

