package dundertext.editor

import dundertext.data.Span

class SpanNode {
  var parent: RowNode = _
  var prev: SpanNode = _
  var next: SpanNode = _
  var start: Int = _
  var end: Int = _
  var text = ""

  def containsPos(pos: Int): Boolean =
    start <= pos && pos < end

  def insert(pos: Int, t: String): Unit = {
    val sb = new StringBuilder(text)
    text = sb.insert(pos - start, t).result()
  }

  def delete(pos: Int): Unit = {
    val sb = new StringBuilder(text)
    text = sb.deleteCharAt(pos).result()
  }

  def length: Int = text.length
}

object SpanNode {
  def from(span: Span) = {
    val r = new SpanNode
    r.text = span.text
    r
  }
}
