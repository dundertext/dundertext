package dundertext.editor

class Cursor {
  var text: TextNode = _
  var row: RowNode = _
  var span: SpanNode = _
  var pos: Int = 0

  def blur(): Unit = {
    text = null
    row = null
    span = null
    pos = 0
  }

  def isAtBeginningOfRow: Boolean =
    pos == 0

  def isAtEndOfRow: Boolean =
    pos == row.length

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
    span = row.spans.last
    pos = row.spans.last.end
  }

  override def toString: String = {
    if (isAtText)
      text.nr + "/" + row.nr + "/" + pos
    else
      ""
  }
}
