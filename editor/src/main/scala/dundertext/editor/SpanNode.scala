package dundertext.editor

import dundertext.data.Span

class SpanNode {
  var prev: SpanNode = _
  var next: SpanNode = _
  var start: Int = _
  var end: Int = _
  val text = new StringBuilder

  def containsPos(pos: Int): Boolean =
    start <= pos && pos < end

  def insert(pos: Int, t: String): Unit = {
    text.insert(pos - start, t)
  }

  def length: Int = text.length
}

object SpanNode {
  def from(span: Span) = {
    val r = new SpanNode
    r.text.append(span.text)
    r
  }

}
