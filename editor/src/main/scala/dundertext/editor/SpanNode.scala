package dundertext.editor

import dundertext.data.Span
import SpanNode._

class SpanNode {
  var parent: RowNode = _
  var prev: SpanNode = _
  var next: SpanNode = _
  var start: Int = _
  var text: String = ""

  def end: Int = start + text.length

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

  def trimLeft(): Unit = {
    text = LeftTrim.replaceFirstIn(text, "")
  }

  def trimRight(): Unit = {
    text = RightTrim.replaceFirstIn(text, "")
  }

  def build() = Span(text)
}

object SpanNode {
  final val LeftTrim = """^\s+""".r
  final val RightTrim = """\s+$""".r

  def from(span: Span) = {
    val r = new SpanNode
    r.text = span.text
    r
  }

  def from(span: String) = {
    val r = new SpanNode
    r.text = span
    r
  }
}
