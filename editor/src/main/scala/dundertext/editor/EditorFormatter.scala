package dundertext.editor

class EditorFormatter(editor: Editor) {

  val sb = new StringBuilder

  def format(): String = {
    editor.buffer.entries foreach format
    sb.toString()
  }

  def format(node: DocumentNode): Unit = node match {
    case n: TextNode => format(n)
    case _ =>
  }

  def format(text: TextNode) {
    for (r <- text.rows) {
      row(r)
    }
    sb.append('\n')
  }

  def row(row: RowNode): Unit = {
    for (s <- row.spans) {
      span(s)
    }
    sb.append('\n')
  }

  def span(span: SpanNode): Unit = {
    if (editor.cursor.span == span)
      writeTextAtCursor(span.text, editor.cursor.pos)
    else
      writeText(span.text)
  }

  def writeText(s: String) = sb.append(s)
  def writeTextAtCursor(s: String, pos: Int) = sb.append(s)
}
