package dundertext.ui.editor

import dundertext.editor.{EditorFormatter, Editor}

import scalatags.Escaping

class EditorHtmlFormatter(editor: Editor) extends EditorFormatter(editor) {

  override def writeText(s: String) = {
    sb.append("<span>")
    Escaping.escape(s, sb)
    sb.append("</span>")
  }

  override def writeTextAtCursor(s: String, cursorPos: Int) = {
    sb.append(s"<span class='cursorat' data-cursor='$cursorPos'>")
    Escaping.escape(s, sb)
    sb.append("</span>")
  }
}
