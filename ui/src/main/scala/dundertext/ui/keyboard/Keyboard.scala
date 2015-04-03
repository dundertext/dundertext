package dundertext.ui.keyboard

trait Keyboard {
  def listen(listener: KeyboardListener): Unit
}

trait KeyboardListener {
  def onKeyDown(chord: KeyChord): Boolean = false
  def onKeyPress(char: Char): Boolean = false
}
