package dundertext.editor.cmd

import dundertext.data.Time

object AdjustTiming {

  object TenthBack extends CommandDescription {
    def apply() = new TenthBack
  }

  class TenthBack extends SubtitlingCommand {
    override def execute(): Unit = {
      val t = cursor.text.prevTime
      if (player.isPaused && player.currentTime == t.time) {
        t.time = Time(t.time.millis - 100)
        buffer.relink()
      } else {
        player.pause()
      }

      player.cue(t.time)
    }
  }

  object TenthForward extends CommandDescription {
    def apply() = new TenthForward
  }

  class TenthForward extends SubtitlingCommand {
    override def execute(): Unit = {
      val t = cursor.text.prevTime
      if (!player.isPaused) {
        player.pause()
      } else {
        t.time = Time(t.time.millis + 100)
        buffer.relink()
      }

      player.cue(t.time)
    }
  }
}
