package dundertext.editor.cmd

object VideoPlayerCommand {

  class CueStart extends SubtitlingCommand {
    override def execute() = {
      player.cueStart()
    }
  }

  class CueEnd extends SubtitlingCommand {
    override def execute() = {
      player.cueEnd()
    }
  }

  object TenthForward extends CommandDescription {
    def apply() = new TenthForward
  }

  class TenthForward extends SubtitlingCommand {
    override def execute() = {
      player.pause()
      player.seek(100)
    }
  }

  object TenthBackward extends CommandDescription {
    def apply() = new TenthBackward
  }

  class TenthBackward extends SubtitlingCommand {
    override def execute() = {
      player.pause()
      player.seek(-100)
    }
  }

  object SecondForward extends CommandDescription {
    def apply() = new SecondForward
  }

  class SecondForward extends SubtitlingCommand {
    override def execute() = {
      player.seek(1000)
    }
  }

  object SecondBackward extends CommandDescription {
    def apply() = new SecondBackward
  }


  class SecondBackward extends SubtitlingCommand {
    override def execute() = {
      player.seek(-1000)
    }
  }

  object TogglePausePlay extends CommandDescription {
    def apply() = new TogglePausePlay
  }

  class TogglePausePlay extends SubtitlingCommand {
    override def execute() = {
      if (player.isPaused) player.play()
      else player.pause()
    }
  }
}
