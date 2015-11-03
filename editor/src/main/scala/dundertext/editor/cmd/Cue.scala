package dundertext.editor.cmd

import dundertext.editor.{TextNode, TimingNode}

object Cue {

  object Prev extends CommandDescription {
    def apply() = new Prev
  }

  class Prev extends SubtitlingCommand {
    override def execute() = {
      val t = editor.player.currentTime
      val prev = editor.buffer.findNodeBeore(t)
      editor.player.cue(prev.time)
      editor.placeCursorAtVideo()
    }
  }

  object Next extends CommandDescription {
    def apply() = new Next
  }

  class Next extends SubtitlingCommand {
    override def execute() = {
      val t = editor.player.currentTime
      val next = editor.buffer.findNodeAfter(t)
      editor.player.cue(next.time)
      editor.placeCursorAtVideo()
    }
  }

}

