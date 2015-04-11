package dundertext.ui.timeline

import dundertext.data.Time
import TimelineWaveformModel._

/**
 * Models one or two images drawn with an offset from the left margin
 * to simulate a horizontal scrolling waveform.
 */
case class TimelineWaveformModel (
  left: Img,
  right: Img
) {
  def isSingle: Boolean = left.minute == right.minute
  def isDouble: Boolean = !isSingle
}

object TimelineWaveformModel {
  final val ImgWavDuration = 60000
  final val ImgWidthPx = 2400
  final val MillisPerPixel = ImgWavDuration / ImgWidthPx
  final val PixelsPerMs = ImgWidthPx.toDouble / ImgWavDuration.toDouble

  case class Img (minute: Int, pixOffset: Int) {
    def src: String =
      if (minute < 0)       ""
      else if (minute < 10) "waveform/0"+minute+".png"
      else                  "waveform/"+minute+".png"
  }

  private def img(ms: Int, origo: Int) = {
    val minute = ms / (60 * 1000)
    val millis = ms % (60 * 1000)
    val pixOffset = (millis * PixelsPerMs).toInt
    Img(minute, origo - pixOffset)
  }

  def calc(videoTime: Time, width: Int): TimelineWaveformModel = {
    val millisHalfScreen = (width / 2) * MillisPerPixel
    val leftEdgeMillis = videoTime.millis - millisHalfScreen
    val rightEdgeMillis = videoTime.millis + millisHalfScreen

    TimelineWaveformModel(
      left = img(leftEdgeMillis, 0),
      right = img(rightEdgeMillis, width)
    )
  }

  def empty = TimelineWaveformModel(Img(-1, -1), Img(-1, -1))
}
