package dundertext.editor

import dundertext.data.{Span, Row}

import scala.collection.mutable

class TextNode private() extends DocumentNode {
  var nr: Int = _

  val rows = mutable.Buffer[RowNode]()

  def firstRow: RowNode = rows.head
  def lastRow: RowNode = rows.last

  def rowCount = rows.length

  def append(s: String): this.type = {
    if (!hasText) rows.clear()
    rows.append(RowNode.from(Row(List(Span(s)))))
    relink()
    this
  }

  def insertRow(prevRow: RowNode, newRow: RowNode): Unit = {
    val pos = rows.indexOf(prevRow)
    rows.insert(pos + 1, newRow)
    relink()
  }

  def remove(node: RowNode): Unit = {
    rows.remove(rows.indexOf(node))
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

  def text: String = {
    val sb = new StringBuilder
    for (row <- rows) {
      sb.append(row.text).append('\n')
    }
    sb.result()
  }

  override def toString: String =
    text

  def hasText: Boolean = {
    rows.exists(_.hasText)
  }
}

object TextNode {
  def empty = {
    val n = new TextNode
    n.append("")
    n
  }
}
