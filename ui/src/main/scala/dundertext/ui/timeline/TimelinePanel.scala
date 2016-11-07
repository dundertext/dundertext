package dundertext.ui.timeline

import dundertext.data._
import dundertext.editor.Editor
import dundertext.ui.video.VideoPlayer
import org.scalajs.dom.{CanvasRenderingContext2D, html}

class TimelinePanel(e: html.Canvas, player: VideoPlayer, editor: Editor) {

  // save last drawn model to avoid constant redraw each frame
  private var last: TimelineWaveformModel = TimelineWaveformModel.empty

  e.width = e.parentElement.clientWidth
  e.height = e.parentElement.clientHeight
  val middlePix: Int = e.width / 2

  private val images = new ImageLoader

  private def draw(model: TimelineWaveformModel, tm: TimelineTextModel): Unit = {
    val ctx = e.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    // clear
    ctx.fillStyle = "#333"
    ctx.fillRect(0, 0, e.width, e.height)

    // waveforms
    ctx.drawImage(images.aImg, model.left.pixOffset, -20)
    if (model.isDouble)
      ctx.drawImage(images.bImg, model.right.pixOffset, -20)

    // text
    for (e <- tm.es) {
      ctx.fillStyle = "#005500"
      ctx.fillRect(e.x, 37, e.w, 14)
      ctx.fillStyle = "#AAA"
      ctx.fillText(e.dt.text.text, e.x + 4, 48, e.w)
    }

    // cursor
    ctx.fillStyle = "#FFFF00"
    ctx.fillRect(middlePix.toDouble, 12, 1, 40)

  }

  private def requestFrame(): Unit = {
    org.scalajs.dom.window.requestAnimationFrame(onAnimationFrame _)
  }

  def calcDisplayed(): List[DisplayedText] = {
    val b = List.newBuilder[DisplayedText]
    var t = editor.buffer.firstText
    while (t != null) {
      b += t.display
      t = t.nextText
    }
    b.result()
  }

  private def onAnimationFrame(nr: Double): Unit = {
    val model = TimelineWaveformModel.calc(player.currentTime, e.width)
    if (model != last) {
      val textModel = TimelineTextModel.calc(model.leftEdgeMillis, calcDisplayed())

      images.load(
        model.left.src,
        if (model.isDouble) model.right.src else ""
      )
      if (images.allLoaded) {
        draw(model, textModel)
        last = model
      }
    }
    requestFrame()
  }

  requestFrame()
}
