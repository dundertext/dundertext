package dundertext.ui.timeline

import dundertext.data.DisplayedText
import dundertext.ui.timeline.TimelineTextModel._
import dundertext.ui.timeline.TimelineWaveformModel.PixelsPerMs

final case class TimelineTextModel (
  es: List[Entry]
)

object TimelineTextModel {
  final case class Entry (
    x: Int,
    w: Int,
    dt: DisplayedText
  )

  def calc(leftEdgeMillis: Int, dts: List[DisplayedText]): TimelineTextModel = {
    val es = for (dt <- dts) yield {
      val x = ((dt.in.millis - leftEdgeMillis) * PixelsPerMs).toInt
      val w = (dt.length.millis * PixelsPerMs).toInt
      Entry(x, w, dt)
    }

    TimelineTextModel(es)
  }
}
