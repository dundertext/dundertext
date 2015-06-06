package dundertext.editor

import dundertext.data._

import scala.collection.mutable
import scala.collection.breakOut

class TextNode private() extends DocumentNode {
  var nr: Int = _

  val rows = mutable.Buffer[RowNode]()

  var display: DisplayedText = _

  def firstRow: RowNode = rows.head
  def lastRow: RowNode = rows.last

  def rowCount = rows.length

  def append(s: String): this.type = {
    if (!hasText) rows.clear()
    rows.append(RowNode.from(Row(List(Span(s)))))
    relink()
    this
  }

  def insertRowAfter(prevRow: RowNode, newRow: RowNode): Unit = {
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

  def text: String = text('\n')

  def text(sep: Char): String = {
    val sb = new StringBuilder
    for (row <- rows) {
      sb.append(row.text).append(sep)
    }
    sb.result()
  }

  override def toString: String =
    text

  def hasText: Boolean = {
    rows.exists(_.hasText)
  }

  def build(): Text = {
    Text(id, (rows map (_.build()))(breakOut))
  }

  def recalcDisplay(): Unit = {
    val in = prevTime.time
    val next = nextTime.time
    val length = Length(next.millis - in.millis - DisplayedText.Separation.millis)
    display = DisplayedText(build(), in, length)
  }
}

object TextNode {
  def empty = {
    val n = new TextNode
    n.append("")
    n
  }
}
