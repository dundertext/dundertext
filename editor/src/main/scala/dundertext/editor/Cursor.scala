package dundertext.editor

class Cursor {

  var text: TextNode = _
  var row: RowNode = _
  var pos: Int = 0

  def isAtText: Boolean =
    text ne null

  def moveTo(t: TextNode): Unit = {
    text = t
    row = t.firstRow
    pos = 0
  }

  def moveTo(r: RowNode): Unit = {
    row = r
    text = r.parent
    pos = 0
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
}
