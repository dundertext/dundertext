package dundertext.ui.editor

import dundertext.editor.cmd._
import dundertext.editor.{Player, DocumentBuffer, Editor}
import dundertext.ui.keyboard.{KeyChord, Keyboard, KeyboardListener}
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
  panel.display("")

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewTextAtVideo)

    editor.execute(new TypeText(char.toString))
    redraw()

    true
  }

  def redraw(): Unit = {
    dom.document.getElementById("status").textContent = editor.cursor.toString

    def html = new EditorHtmlFormatter(editor).format()
    panel.display(html)
    if (editor.cursor.isAtText) {
      placeEditorCursor()
      svgDisplay.display(editor.cursor.row.text, "", editor.cursor.pos)
    } else {
      svgDisplay.display("", "", 0)
    }

  }

  def placeEditorCursor(): Unit = {
    panel.focus()
    val selection: Selection = dom.window.getSelection()
    val cs: html.Span = panel.cursorSpan
    selection.collapse(cs.firstChild, editor.cursor.pos)
  }

  val keysToCommands: Map[KeyChord, List[CommandDescription]] = Map(
    KeyChord(KeyCode.enter)     -> List(AddRow),
    KeyChord(KeyCode.backspace) -> List(DeleteChar.Left, MergeRows),
    KeyChord(KeyCode.left)      -> List(MoveCursor.Left),
    KeyChord(KeyCode.right)     -> List(MoveCursor.Right),
    KeyChord(KeyCode.up)        -> List(MoveCursor.Up),
    KeyChord(KeyCode.down)      -> List(MoveCursor.Down),
    KeyChord(KeyCode.space)     -> List(Space),
    KeyChord(KeyCode.home)      -> List(MoveCursor.RowBegin),
    KeyChord(KeyCode.end)       -> List(MoveCursor.RowEnd),
    KeyChord(KeyCode.escape)    -> List(BlurCursor)
  )

  override def onKeyDown(chord: KeyChord): Boolean = {
    dom.document.getElementById("status").textContent = ""
    val cmds = keysToCommands.getOrElse(chord, Nil)
    editor.execute(cmds)
    redraw()
    cmds.nonEmpty
  }

  def execute(cmd: Class[_ <: SubtitlingCommand]): Unit = {
    if (cmd == classOf[AddRow]) new AddRow
  }
}
