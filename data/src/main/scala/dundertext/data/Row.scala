package dundertext.data

final case class Row (
  spans: List[Span]
) {
  def text = spans.map(_.text).mkString

  def split(splitpos: Int): (Row, Row) = {
    val left = List.newBuilder[Span]
    val right = List.newBuilder[Span]
    var spanStart = 0
    for (s <- spans) {
      val spanEnd = spanStart + s.text.length
      if (spanEnd <= splitpos)
        left += s
      else if (spanStart >= splitpos)
        right += s
      else {
        val (sl, sr) = s.split(splitpos - spanStart)
        left += sl
        right += sr
      }

      spanStart = spanEnd
    }
    (Row(left.result()), Row(right.result()))
  }

}
