package dundertext.ui.editor

import org.scalajs.dom.html
import org.scalajs.dom

class EditorPanel(e: html.Element) {
  val pre: html.Pre = e.appendChild(dom.document.createElement("pre")).asInstanceOf

  def display(document: String): Unit = {
    pre.innerHTML = document
  }
}
