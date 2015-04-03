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

