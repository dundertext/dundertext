package dundertext.ui.timeline

import dundertext.ui.video.VideoPlayer
import org.scalajs.dom.{CanvasRenderingContext2D, html}

class TimelinePanel(e: html.Canvas, player: VideoPlayer) {

  // save last drawn model to avoid constant redraw each frame
  private var last: TimelineWaveformModel = TimelineWaveformModel.empty

  e.width = e.parentElement.clientWidth
  e.height = e.parentElement.clientHeight
  val middlePix: Int = e.width / 2

  private val images = new ImageLoader

  private def draw(model: TimelineWaveformModel): Unit = {
    val ctx = e.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

    // clear
    ctx.fillStyle = "#333"
    ctx.fillRect(0, 0, e.width, e.height)

    // waveforms
    ctx.drawImage(images.aImg, model.left.pixOffset, -20)
    if (model.isDouble)
      ctx.drawImage(images.bImg, model.right.pixOffset, -20)

    // cursor
    ctx.fillStyle = "#FFFF00"
    ctx.fillRect(middlePix.toDouble, 12, 1, 40)
  }

  private def requestFrame(): Unit = {
    org.scalajs.dom.requestAnimationFrame(onAnimationFrame _)
  }

  private def onAnimationFrame(nr: Double): Unit = {
    val model = TimelineWaveformModel.calc(player.currentTime, e.width)
    if (model != last) {
      images.load(
        model.left.src,
        if (model.isDouble) model.right.src else ""
      )
      if (images.allLoaded) {
        draw(model)
        last = model
      }
    }
    requestFrame()
  }

  requestFrame()
}
