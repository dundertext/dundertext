package dundertext.ui.timeline

import org.scalajs.dom.html
import org.scalajs.dom.document

class ImageLoader {
  val aImg = document.createElement("img").asInstanceOf[html.Image]
  val bImg = document.createElement("img").asInstanceOf[html.Image]

  private var aSrc = ""
  private var bSrc = ""

  def allLoaded: Boolean = {
    (aSrc.isEmpty || aImg.complete && aImg.naturalHeight > 0) &&
    (bSrc.isEmpty || bImg.complete && bImg.naturalHeight > 0)
  }

  def load(aSrc: String, bSrc: String): Unit = {
    if (aSrc != this.aSrc) {
      this.aSrc = aSrc
      aImg.src = aSrc
    }

    if (bSrc != this.bSrc) {
      this.bSrc = bSrc
      bImg.src = bSrc
    }
  }

  override def toString = s"ImageLoader($aSrc, $bSrc, $allLoaded)"
}
