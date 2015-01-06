package dundertext.ui.keyboard

trait Keyboard {
  def listen(listener: KeyboardListener)
}

trait KeyboardListener {
  def onKeyDown(code: Int): Boolean = false
  def onKeyPress(char: Char): Boolean = false
}
