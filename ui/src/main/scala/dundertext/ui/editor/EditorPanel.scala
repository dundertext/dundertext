package dundertext.ui.editor

import org.scalajs.dom.html

class EditorPanel(e: html.Element) {
  def display(document: String): Unit = {
    e.innerHTML = document
  }
}
