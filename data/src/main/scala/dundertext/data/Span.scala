package dundertext.data

final case class Span (
  text: String
) {
  def split(splitpos: Int): (Span, Span) = {
    val (l, r) = text.splitAt(splitpos)
    (Span(l), Span(r))
  }
}
