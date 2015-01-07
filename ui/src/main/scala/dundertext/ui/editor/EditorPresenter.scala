package dundertext.ui.editor

import dundertext.editor.cmd.{TypeTextCommand, NewTextCommand}
import dundertext.editor.{DocumentBuffer, Editor}
import dundertext.ui.keyboard.{Keyboard, KeyboardListener}
import org.scalajs.dom.extensions.KeyCode

class EditorPresenter(keyboard: Keyboard, panel: EditorPanel) extends KeyboardListener {

  keyboard.listen(this)
  val editor = Editor(DocumentBuffer.empty)

  panel.display("TJOHO: ")

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewTextCommand)

    editor.execute(new TypeTextCommand(char.toString))
    panel.display(editor.buffer.asText)

    true
  }

  override def onKeyDown(code: Int) = {
    code match {
      case KeyCode.enter  => editor.execute(new NewTextCommand)
                             true

      case _              => false
    }

  }
}
