package dundertext.editor

import dundertext.data.{Span, Row}

import scala.collection.mutable
import scala.collection.breakOut

class RowNode {
  var parent: TextNode = _
  var prev: RowNode = _
  var next: RowNode = _
  var nr: Int = _

  var spans = mutable.Buffer[SpanNode]()

  def length: Int = spans.map(_.length).sum
  def hasText: Boolean = length > 0
  def isEmpty: Boolean = length == 0
  def isLast: Boolean = next == null

  def insert(pos: Int, text: String): Unit = {
    val s = spans.find(_.containsPos(pos)) getOrElse spans.last
    s.insert(pos, text)
  }

  def delete(pos: Int): Unit = {
    val s = spans.find(_.containsPos(pos)) getOrElse spans.last
    s.delete(pos)
  }

  def remove(): Unit = {
    parent.remove(this)
  }

  def relink(): Unit = {
    var p = 0
    for (s <- spans) {
      s.parent = this
      s.start = p
      p = s.end
    }
  }

  def text: String = {
    val sb = new StringBuilder()
    asText(sb)
    sb.result()
  }

  def asText(sb: StringBuilder): Unit = {
    for (s <- spans)
      sb.append(s.text)
  }

  def asSpans(): List[Span] =
    (spans map (_.build()))(breakOut)

  def clear(): Unit = {
    spans.clear()
  }

  def append(ss: List[Span]): Unit = {
    spans.appendAll(ss map SpanNode.from)
    relink()
  }

  def set(row: Row): Unit = {
    clear()
    append(row.spans)
  }

  def trim(): Unit = {
    spans.last.trimRight()
  }

  def build(): Row = {
    Row(asSpans())
  }
}

object RowNode {
  def from(row: Row): RowNode = {
    val r = new RowNode
    r.append(row.spans)
    r
  }

  def from(row: String): RowNode = {
    val r = new RowNode
    r.spans += SpanNode.from(row)
    r
  }
}
