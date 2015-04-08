package dundertext.ui.video

import dundertext.ui.keyboard.{Keyboard, KeyboardListener}
import org.scalajs.dom

class VideoPlayerPresenter(keyboard: Keyboard, panel: VideoPlayerPanel) extends KeyboardListener {
  val player: VideoPlayer = panel.player

  // init
  keyboard.listen(this)
  dom.setInterval(redraw _, 100)

  def redraw(): Unit = {
    panel.time.textContent = player.currentTime.formatShort

    if (player.until != null && player.currentTime.millis > player.until.millis) {
      player.pause()
      player.cue(player.until)
      player.until = null
    }
  }
}
