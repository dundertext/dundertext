package dundertext.editor

class EditorFormatter(editor: Editor) {

  val sb = new StringBuilder

  def format(): String = {
    editor.buffer.entries foreach format
    sb.toString()
  }

  def format(node: DocumentNode): Unit = node match {
    case n: TextNode => format(n)
    case n: TimingNode => format(n)
    case _ =>
  }

  def format(text: TextNode): Unit = {
    for (r <- text.rows) {
      row(r)
    }
    sb.append('\n')
  }

  def format(timing: TimingNode): Unit = {
    writeTiming(timing.time.formatShort)
    sb.append('\n')
  }

  def row(row: RowNode): Unit = {
    sb.append("  ")
    for (s <- row.spans) {
      span(s)
    }
    sb.append('\n')
  }

  def span(span: SpanNode): Unit = {
    if (span == editor.cursor.span)
      writeTextAtCursor(span.text, editor.cursor.pos)
    else
      writeText(span.text)
  }

  def writeTiming(s: String) = sb.append(s)
  def writeText(s: String) = sb.append(s)
  def writeTextAtCursor(s: String, pos: Int) = sb.append(s)
}
