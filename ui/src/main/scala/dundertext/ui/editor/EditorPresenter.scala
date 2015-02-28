package dundertext.ui.editor

import dundertext.editor.cmd.{AddRow, DeleteChar, TypeText, NewText}
import dundertext.editor.{DocumentBuffer, Editor}
import dundertext.ui.keyboard.{Keyboard, KeyboardListener}
import org.scalajs.dom.extensions.KeyCode

class EditorPresenter(keyboard: Keyboard, panel: EditorPanel) extends KeyboardListener {

  keyboard.listen(this)
  val editor = Editor(DocumentBuffer.empty)

  panel.display("TJOHO: ")

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewText)

    editor.execute(new TypeText(char.toString))
    panel.display(editor.buffer.asText)

    true
  }

  override def onKeyDown(code: Int) = {
    val handled = code match {
      case KeyCode.enter  => editor.execute(new AddRow)
                             true

//      case KeyCode.enter  => editor.execute(new NewText)
//                             true

      case KeyCode.backspace => editor.execute(new DeleteChar)
                             true

      case _              => false
    }

    if (handled)
      panel.display(editor.buffer.asText)

    handled
  }
}
