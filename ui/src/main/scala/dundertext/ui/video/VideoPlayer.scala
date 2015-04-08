package dundertext.ui.video

import org.scalajs.dom.html
import dundertext.data._

class VideoPlayer(e: html.Video) extends dundertext.editor.Player {
  e.src = "../../../videos/example.webm"
  e.play()
  var until: Time = _

  def isPaused: Boolean = e.paused
  def currentTime: Time = Time.fromSecondsRounded(e.currentTime)

  def play(): Unit = e.play()
  def pause(): Unit = {
    e.pause()
  }

  def cue(t: Time): Unit = {
    e.currentTime = t.millis / 1000.0d
  }

  def cueStart(): Unit = e.currentTime = 0.0d
  def cueEnd(): Unit = e.currentTime = e.duration

  def seek(offsetMillis: Int): Unit = e.currentTime += offsetMillis / 1000.0d

  override def playUntil(time: Time): Unit = {
    until = time
    play()
  }

}
