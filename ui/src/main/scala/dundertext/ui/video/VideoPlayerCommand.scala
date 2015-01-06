package dundertext.ui.video

sealed abstract class VideoPlayerCommand {
  var player: VideoPlayer = _
  def execute(): Unit
}

object VideoPlayerCommand {

  object None extends VideoPlayerCommand {
    override def execute() = {}
  }

  class CueStart extends VideoPlayerCommand {
    override def execute() = {
      player.cueStart()
    }
  }

  class CueEnd extends VideoPlayerCommand {
    override def execute() = {
      player.cueEnd()
    }
  }

  class TenthForward extends VideoPlayerCommand {
    override def execute() = {
      player.pause()
      player.seek(100)
    }
  }

  class TenthBackward extends VideoPlayerCommand {
    override def execute() = {
      player.pause()
      player.seek(-100)
    }
  }

  class SecondForward extends VideoPlayerCommand {
    override def execute() = {
      player.seek(1000)
    }
  }

  class SecondBackward extends VideoPlayerCommand {
    override def execute() = {
      player.seek(-1000)
    }
  }

  class TogglePausePlay extends VideoPlayerCommand {
    override def execute() = {
      if (player.isPaused) player.play()
      else player.pause()
    }
  }

}

