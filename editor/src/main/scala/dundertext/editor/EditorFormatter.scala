package dundertext.editor

class EditorFormatter(editor: Editor) {

  val sb = new StringBuilder

  def format(): String = {
    editor.buffer.entries foreach format
    sb.toString()
  }

  private def format(node: DocumentNode): Unit = node match {
    case n: TextNode => format(n)
    case n: TimingNode if !n.isEndNode => format(n)
    case _ =>
  }

  private def format(text: TextNode): Unit = {
    for (r <- text.rows) {
      row(r)
    }
  }

  private def format(timing: TimingNode): Unit = {
    writeTiming(timing.time.formatShort)
    sb.append('\n')
  }

  private def row(row: RowNode): Unit = {
    sb.append("  ")
    for (s <- row.spans) {
      span(s)
    }
    sb.append('\n')
  }

  private def span(span: SpanNode): Unit = {
    if (span == editor.cursor.span)
      writeTextAtCursor(span.text, editor.cursor.pos)
    else
      writeText(span.text)
  }

  protected def writeTiming(s: String) = sb.append(s)
  protected def writeText(s: String) = sb.append(s)
  protected def writeTextAtCursor(s: String, pos: Int) = sb.append(s)
}
