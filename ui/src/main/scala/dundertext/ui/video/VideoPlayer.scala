package dundertext.ui.video

import org.scalajs.dom.HTMLVideoElement
import dundertext.data._

class VideoPlayer(e: HTMLVideoElement) {
  def isPaused: Boolean = e.paused
  def currentTime: Time = Time.fromSeconds(e.currentTime)

  def play(): Unit = e.play()
  def pause(): Unit = {
    e.pause()
  }

  def cue(t: Time): Unit = {
    e.currentTime = t.millis / 1000.0d
  }

  def cueStart(): Unit = e.currentTime = 0.0d
  def cueEnd(): Unit = e.currentTime = e.duration

  def seek(offsetMillis: Long): Unit = e.currentTime += offsetMillis / 1000.0d
}
