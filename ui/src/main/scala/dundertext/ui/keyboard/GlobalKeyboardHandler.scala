package dundertext.ui.keyboard

import org.scalajs.dom
import org.scalajs.dom.KeyboardEvent

class GlobalKeyboardHandler extends Keyboard {
  dom.document.onkeydown = onKeyDown _
  dom.document.onkeypress = onKeyPress _

  var listeners: Set[KeyboardListener] = Set.empty

  def onKeyDown(e: KeyboardEvent): Unit = {
    val chord = KeyChord(e.keyCode)
    for (l <- listeners) {
      val handled = l.onKeyDown(chord)
      if (handled) e.preventDefault()
    }
  }

  def onKeyPress(e: KeyboardEvent): Unit = {
    for (l <- listeners) {
      val handled = l.onKeyPress(e.keyCode.toChar)
      if (handled) e.preventDefault()
    }
  }

  override def listen(listener: KeyboardListener) = {
    listeners += listener
  }
}
