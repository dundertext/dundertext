package dundertext.ui.editor

import org.scalajs.dom.HTMLElement

class EditorPanel(e: HTMLElement) {
  def display(document: String): Unit = {
    e.innerHTML = document
  }
}
