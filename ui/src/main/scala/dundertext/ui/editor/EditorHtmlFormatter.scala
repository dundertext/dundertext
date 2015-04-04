package dundertext.ui.editor

import dundertext.editor.{Editor, EditorFormatter}
import dundertext.ui.editor.EditorHtmlFormatter._

import scalatags.Escaping

class EditorHtmlFormatter(editor: Editor) extends EditorFormatter(editor) {

  override def writeText(s: String) = {
    sb.append("<span>")
    writeEscaped(s)
    sb.append("</span>")
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
      Escaping.escape(s.replace(' ', NbSp), sb)
  }
}

object EditorHtmlFormatter {
  final val NbSp = '\u00a0'
  final val ZwSp = '\u200b'
}
