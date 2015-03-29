package dundertext.ui.editor

import dundertext.editor.cmd._
import dundertext.editor.{Player, DocumentBuffer, Editor}
import dundertext.ui.keyboard.{Keyboard, KeyboardListener}
import dundertext.ui.svg.SvgDisplay
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html
import org.scalajs.dom.raw.Selection

class EditorPresenter(
    keyboard: Keyboard,
    panel: EditorPanel,
    svgDisplay: SvgDisplay,
    player: Player
) extends KeyboardListener {

  keyboard.listen(this)
  val editor = Editor(DocumentBuffer.empty)
  editor.player = player
  panel.display("Editor")

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewTextAtVideo)

    editor.execute(new TypeText(char.toString))
    redraw()

    true
  }

  def redraw(): Unit = {
    def html = new EditorHtmlFormatter(editor).format()
    panel.display(html)
    if (editor.cursor.isAtText) {
      placeEditorCursor()
      svgDisplay.display(editor.cursor.row.text, "", editor.cursor.pos)
    } else {
      svgDisplay.display("", "", 0)
    }

    dom.document.getElementById("status").textContent = editor.cursor.toString
  }

  def placeEditorCursor(): Unit = {
    panel.focus()
    val selection: Selection = dom.window.getSelection()
    val cs: html.Span = panel.cursorSpan
    selection.collapse(cs.firstChild, editor.cursor.pos)
  }

  override def onKeyDown(code: Int): Boolean = {
    val handled = code match {
      case KeyCode.enter     => editor.execute(new AddRow); true
      case KeyCode.backspace => editor.execute(new DeleteChar); true
      case KeyCode.left      => editor.execute(new MoveCursor.Left); true
      case KeyCode.right     => editor.execute(new MoveCursor.Right); true
      case KeyCode.up        => editor.execute(new MoveCursor.Up); true
      case KeyCode.down      => editor.execute(new MoveCursor.Down); true
      case KeyCode.space     => editor.execute(new Space); true
      case KeyCode.home      => editor.execute(new MoveCursor.RowBegin); true
      case KeyCode.escape    => editor.execute(new BlurCursor); true

      case _ => false
    }

    if (handled)
      redraw()

    handled
  }
}
