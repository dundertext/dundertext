package dundertext.ui.video

import org.scalajs.dom
import org.scalajs.dom.html

class VideosPanel(tr: html.TableRow) {
  val td: html.Element = dom.document.createElement("td").asInstanceOf
  tr.appendChild(td)
  val left: VideoPlayerPanel = new VideoPlayerPanel(td)
}
