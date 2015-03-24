package dundertext.ui.video

import dundertext.ui.svg.SvgDisplay
import org.scalajs.dom.html
import org.scalajs.dom

class VideoPlayerPanel(e: html.Element) {
  e.innerHTML =
    "<div style='position: relative; width: 640px; height: 360px;'>" +
      "<video width='640px' height='360px' style='position: absolute'></video>" +
      "<svg width='640px' height='360px' viewBox='0 0 1280 720' style='position: absolute'></svg>" +
    "</div>" +
    "<time></time>"

  val container: html.Div = e.firstElementChild.asInstanceOf[html.Div]
  val video: html.Video = container.getElementsByTagName("video").item(0).asInstanceOf[html.Video]
  val svgElement: dom.svg.Element = container.getElementsByTagName("svg").item(0).asInstanceOf[dom.svg.Element]
  val time: html.Element = e.getElementsByTagName("time").item(0).asInstanceOf[html.Element]

  val player = new VideoPlayer(video)
  val display = new SvgDisplay(svgElement)
}
