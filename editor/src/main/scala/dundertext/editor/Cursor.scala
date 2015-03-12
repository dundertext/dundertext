package dundertext.editor

class Cursor {
  def isAtBeginningOfRow: Boolean =
    pos == 0

  def isAtEndOfRow: Boolean =
    pos == row.length

  var text: TextNode = _
  var row: RowNode = _
  var span: SpanNode = _
  var pos: Int = 0

  def isAtText: Boolean =
    text ne null

  def moveTo(t: TextNode): Unit = {
    text = t
    row = t.firstRow
    span = row.spans.head
    pos = 0
  }

  def moveTo(r: RowNode, p: Int = 0): Unit = {
    row = r
    text = r.parent
    span = row.spans.head
    pos = p
  }

  def moveRight(d: Int): Unit = {
    pos += d
  }

  def moveLeft(d: Int): Unit = {
    pos -= d
  }

  def moveRowEnd(): Unit = {
    pos = row.spans.last.end
  }

  override def toString: String = {
    text.nr + "/" + row.nr + "/" + pos
  }
}
