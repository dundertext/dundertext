package dundertext.ui.keyboard

final case class KeyChord (
  code: Int,
  shift: Boolean = false,
  ctrl: Boolean = false,
  alt: Boolean = false
) {
  def description: String = {
    toString
  }
}

object KeyChord {
  def Alt(code: Int) = KeyChord(code, alt = true)
  def Ctrl(code: Int) = KeyChord(code, ctrl = true)
}

