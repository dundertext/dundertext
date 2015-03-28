package dundertext.editor

import dundertext.data.{Span, Row}

import scala.collection.mutable

class TextNode extends DocumentNode {
  var nr: Int = _

  val rows = mutable.Buffer[RowNode]()

  def firstRow: RowNode = rows.head
  def lastRow: RowNode = rows.last

  def rowCount = rows.length

  def append(s: String): this.type = {
    rows.append(RowNode.from(Row(List(Span(s)))))
    relink()
    this
  }

  def insertRow(prevRow: RowNode, newRow: RowNode): Unit = {
    val pos = rows.indexOf(prevRow)
    rows.insert(pos + 1, newRow)
    relink()
  }

  def relink(): Unit = {
    var prev: RowNode = null
    var count = 0
    for (r <- rows) {
      count += 1
      r.parent = this
      r.nr = count
      if (prev ne null)
        prev.next = r
      r.prev = prev
      prev = r
    }
  }

  override def asText(sb: StringBuilder): Unit = {
    for (r <- rows) {
      r.asText(sb)
      sb.append('\n')
    }
  }

  def text: String = {
    val sb = new StringBuilder
    asText(sb)
    sb.result()
  }
}

object TextNode {
  def empty = {
    val n = new TextNode
    n.append("")
    n
  }
}
