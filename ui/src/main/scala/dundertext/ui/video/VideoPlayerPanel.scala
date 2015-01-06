package dundertext.ui.video

import org.scalajs.dom.{SVGElement, HTMLElement, HTMLVideoElement}

class VideoPlayerPanel(e: HTMLElement) {
  val player = new VideoPlayer(e.getElementsByTagName("video")(0).asInstanceOf[HTMLVideoElement])
  //val svg = new SvgSubtitle(e.getElementsByTagName("svg")(0).asInstanceOf[SVGElement])
  val time = e.getElementsByTagName("time")(0).asInstanceOf[HTMLElement]
}
