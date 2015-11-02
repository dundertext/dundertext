package dundertext.ui.editor

import dundertext.editor.{Editor, EditorFormatter}
import dundertext.ui.editor.EditorHtmlFormatter._
import dundertext.ui.util.Html

class EditorHtmlFormatter(editor: Editor) extends EditorFormatter(editor) {

  override def writeText(s: String) = {
    sb.append("<span>")
    writeEscaped(s)
    sb.append("</span>")
  }

  override def writeTiming(s: String) = {
    sb.append("<time>")
    sb.append(s)
    sb.append("</time>")
  }

  override def writeTextAtCursor(s: String, cursorPos: Int) = {
    sb.append(s"<span class='cursorat' data-cursor='$cursorPos'>")
    writeEscaped(s)
    sb.append("</span>")
  }

  private def writeEscaped(s: String): Unit = {
    if (s.isEmpty)
      sb.append(NbSp)
    else
      sb.append(Html.escapeText(s.replace(' ', NbSp)))
  }
}

object EditorHtmlFormatter {
  final val NbSp = '\u00a0'
  final val ZwSp = '\u200b'
}
