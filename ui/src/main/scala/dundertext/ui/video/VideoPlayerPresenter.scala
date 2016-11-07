package dundertext.ui.video

import dundertext.ui.keyboard.{Keyboard, KeyboardListener}

import scala.scalajs.js

class VideoPlayerPresenter(keyboard: Keyboard, panel: VideoPlayerPanel) extends KeyboardListener {
  val player: VideoPlayer = panel.player

  // init
  keyboard.listen(this)
  js.timers.setInterval(100)(redraw _)

  def redraw(): Unit = {
    panel.time.textContent = player.currentTime.formatShort

    if (player.until != null && player.currentTime.millis > player.until.millis) {
      player.pause()
      player.cue(player.until)
      player.until = null
    }
  }
}
