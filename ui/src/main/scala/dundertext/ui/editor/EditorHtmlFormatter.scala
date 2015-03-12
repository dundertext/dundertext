package dundertext.ui.editor

import dundertext.editor.{EditorFormatter, Editor}

import scalatags.Escaping

class EditorHtmlFormatter(editor: Editor) extends EditorFormatter(editor) {

  override def writeText(s: String) = {
    sb.append("<span>")
    val s2 = if (s.isEmpty) " " else s
    Escaping.escape(s2, sb)
    sb.append("</span>")
  }

  override def writeTextAtCursor(s: String, cursorPos: Int) = {
    sb.append(s"<span class='cursorat' data-cursor='$cursorPos'>")
    val s2 = if (s.isEmpty) " " else s
    Escaping.escape(s2, sb)
    sb.append("</span>")
  }
}
