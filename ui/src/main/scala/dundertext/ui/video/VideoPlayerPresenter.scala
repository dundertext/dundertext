package dundertext.ui.video

import dundertext.ui.keyboard.{KeyCodes, Keyboard, KeyboardListener}
import org.scalajs.dom

class VideoPlayerPresenter(keyboard: Keyboard, panel: VideoPlayerPanel) extends KeyboardListener {
  val player: VideoPlayer = panel.player

  // init
  keyboard.listen(this)
  dom.setInterval(redraw _, 100)

  override def onKeyDown(code: Int): Boolean = {
    val cmd: VideoPlayerCommand =
      code match {
        case KeyCodes.NumPad7 => new VideoPlayerCommand.CueStart
        case KeyCodes.NumPad9 => new VideoPlayerCommand.CueEnd
        case KeyCodes.NumPad5 => new VideoPlayerCommand.TogglePausePlay
        case KeyCodes.NumPad4 => new VideoPlayerCommand.TenthBackward
        case KeyCodes.NumPad6 => new VideoPlayerCommand.TenthForward
        case KeyCodes.NumPad1 => new VideoPlayerCommand.SecondBackward
        case KeyCodes.NumPad3 => new VideoPlayerCommand.SecondForward
        case _ => VideoPlayerCommand.None
      }

    (cmd != VideoPlayerCommand.None) && {
      cmd.player = player
      println("Executing: " + cmd)
      cmd.execute()
      redraw()
      true
    }
  }

  def redraw(): Unit = {
    panel.time.textContent = player.currentTime.formatShort
  }
}
