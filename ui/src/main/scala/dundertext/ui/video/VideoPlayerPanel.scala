package dundertext.ui.video

import org.scalajs.dom.html
import org.scalajs.dom

class VideoPlayerPanel(e: html.Element) {
  val video: html.Video = e.appendChild(dom.document.createElement("video")).asInstanceOf
  val time: html.Element = e.appendChild(dom.document.createElement("time")).asInstanceOf

  val player = new VideoPlayer(video)
  //val svg = new SvgSubtitle(e.getElementsByTagName("svg")(0).asInstanceOf[SVGElement])
}
