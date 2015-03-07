package dundertext.ui.editor

import org.scalajs.dom.html
import org.scalajs.dom

class EditorPanel(e: html.Element) {
  val pre: html.Pre = e.appendChild(dom.document.createElement("pre")).asInstanceOf
  pre.contentEditable = "true"

  def display(document: String): Unit =
    pre.innerHTML = document

  def focus(): Unit =
    pre.focus()

  def cursorSpan: html.Span =
    pre.getElementsByClassName("cursorat")(0).asInstanceOf[html.Span]
}
