package dundertext.ui.video

import org.scalajs.dom.html

class VideoPlayerPanel(e: html.Element) {
  val player = new VideoPlayer(e.getElementsByTagName("video")(0).asInstanceOf[html.Video])
  //val svg = new SvgSubtitle(e.getElementsByTagName("svg")(0).asInstanceOf[SVGElement])
  val time = e.getElementsByTagName("time")(0).asInstanceOf[html.Element]
}
