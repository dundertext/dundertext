package dundertext.data

final case class Span(text: String) {
  assert(text.indexOf('\n') == -1)

  def split(splitpos: Int): (Span, Span) = {
    val (l, r) = text.splitAt(splitpos)
    (Span(l), Span(r))
  }
}
