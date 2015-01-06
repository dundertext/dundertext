package dundertext.editor

class Cursor {
  var text: TextNode = _
  var row: RowNode = _
  var pos: Int = 0

  def moveTo(t: TextNode): Unit = {
    text = t
    row = t.firstRow
  }

  def moveRowEnd(): Unit = {
    pos = row.spans.last.end
  }
}
