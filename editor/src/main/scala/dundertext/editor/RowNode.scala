package dundertext.editor

import dundertext.data.{Row, Span}

import scala.collection.mutable.ArrayBuffer

class RowNode {
  var spans = ArrayBuffer[SpanNode]()

  def insert(pos: Int, text: String): Unit = {
    val s = spans.find(_.containsPos(pos)) getOrElse spans.last
    s.insert(pos, text)
  }

  def relink(): Unit = {
    var p = 0
    for (s <- spans) {
      s.start = p
      p = p + s.length
      s.end = p
    }
  }

  def asText(sb: StringBuilder): Unit = {
    for (s <- spans)
      sb.append(s.text)

    sb.append('\n')
  }
}

object RowNode {
  def from(row: Row): RowNode = {
    val r = new RowNode
    val spanNodes: List[SpanNode] = row.spans map SpanNode.from
    r.spans appendAll spanNodes
    r.relink()
    r
  }
}
