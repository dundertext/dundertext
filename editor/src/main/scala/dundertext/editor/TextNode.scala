package dundertext.editor

import dundertext.data.{Span, Row}

import scala.collection.mutable.ArrayBuffer

class TextNode extends DocumentNode {

  val rows = ArrayBuffer[RowNode]()

  def firstRow: RowNode = rows(0)

  def rowCount = rows.length

  def append(s: String): this.type = {
    rows.append(RowNode.from(Row(List(Span(s)))))
    this
  }

  override def asText(sb: StringBuilder): Unit = {
    for (r <- rows)
      r.asText(sb)

    sb.append('\n')
  }

  def text: String = {
    val sb = new StringBuilder
    asText(sb)
    sb.result()
  }
}
