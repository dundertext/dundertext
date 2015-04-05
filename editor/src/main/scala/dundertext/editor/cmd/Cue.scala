package dundertext.editor.cmd

import dundertext.editor.{TextNode, TimingNode}

object Cue {

  object Prev extends CommandDescription {
    def apply() = new Prev
  }

  class Prev extends SubtitlingCommand {
    override def execute() = {
      val current = cursor.text.prevTime
      val prevTime: TimingNode = current.prevTime
      editor.player.cue(prevTime.time)
      val text: TextNode = prevTime.nextText
      cursor.moveTo(text)
    }
  }

  object Next extends CommandDescription {
    def apply() = new Next
  }

  class Next extends SubtitlingCommand {
    override def execute() = {
      val nextTime = cursor.text.nextTime
      editor.player.cue(nextTime.time)
      val text: TextNode = nextTime.nextText
      cursor.moveTo(text)
    }
  }

}

