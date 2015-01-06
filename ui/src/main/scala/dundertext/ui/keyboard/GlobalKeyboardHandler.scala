package dundertext.ui.keyboard

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent

class GlobalKeyboardHandler extends Keyboard {
  dom.document.onkeydown = onKeyDown _
  dom.document.onkeypress = onKeyPress _

  var listeners: Set[KeyboardListener] = Set.empty

  def onKeyDown(e: KeyboardEvent): Unit = {
    println("keydown: code")
    println(e.keyCode)
    for (l <- listeners) {
      val handled = l.onKeyDown(e.keyCode)
      if (handled) e.preventDefault()
    }
  }

  def onKeyPress(e: KeyboardEvent): Unit = {
    println("keypress: " + e.keyCode.toChar)
    for (l <- listeners) {
      val handled = l.onKeyPress(e.keyCode.toChar)
      if (handled) e.preventDefault()
    }
  }

  override def listen(listener: KeyboardListener) = {
    listeners += listener
  }
}
