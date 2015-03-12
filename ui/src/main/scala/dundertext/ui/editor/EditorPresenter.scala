package dundertext.ui.editor

import dundertext.editor.cmd._
import dundertext.editor.{DocumentBuffer, Editor}
import dundertext.ui.keyboard.{Keyboard, KeyboardListener}
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.{Node, Selection}

class EditorPresenter(keyboard: Keyboard, panel: EditorPanel) extends KeyboardListener {

  keyboard.listen(this)
  val editor = Editor(DocumentBuffer.empty)

  panel.display("Editor")

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewText)

    editor.execute(new TypeText(char.toString))
    redraw()

    true
  }

  def redraw(): Unit = {
    def html = new EditorHtmlFormatter(editor).format()
    panel.display(html)
    placeEditorCursor()
    dom.document.getElementById("status").textContent = editor.cursor.toString
  }

  def placeEditorCursor(): Unit = {
    panel.focus()
    val selection: Selection = dom.window.getSelection()
    val cs: html.Span = panel.cursorSpan
    selection.collapse(cs.firstChild, editor.cursor.pos)
  }

  override def onKeyDown(code: Int) = {
    val handled = code match {
      case KeyCode.enter  => editor.execute(new AddRow)
                             true

      case KeyCode.backspace => editor.execute(new DeleteChar)
                             true

      case KeyCode.left => editor.execute(new MoveCursor.Left)
                             true

      case KeyCode.right => editor.execute(new MoveCursor.Right)
                             true

      case KeyCode.up => editor.execute(new MoveCursor.Up)
                             true

      case KeyCode.down => editor.execute(new MoveCursor.Down)
                             true

      case KeyCode.space => editor.execute(new Space)
                             true

      case _              => false
    }

    if (handled)
      redraw()

    handled
  }
}
