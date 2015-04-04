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

    def editorHtml = new EditorHtmlFormatter(editor).format()
    panel.display(editorHtml)
    if (editor.cursor.isAtText)
      placeEditorCursor()
    else
      dom.document.getElementById("blur").asInstanceOf[html.Input].focus()

    svgDisplay.display(editor.cursor)
  }

  def placeEditorCursor(): Unit = {
    panel.focus()
    val selection: Selection = dom.window.getSelection()
    val cs: html.Span = panel.cursorSpan
    selection.collapse(cs.firstChild, editor.cursor.pos)
  }

  val keysToCommands: Map[KeyChord, List[CommandDescription]] = Map(
    KeyChord(KeyCode.enter)     -> List(AddRow, BlurOnEmptyLast),
    KeyChord(KeyCode.backspace) -> List(DeleteChar.Left, MergeRows, DeleteRow),
    KeyChord(KeyCode.delete)    -> List(DeleteChar.Right),
    KeyChord(KeyCode.left)      -> List(MoveCursor.Left),
    KeyChord(KeyCode.right)     -> List(MoveCursor.Right),
    KeyChord(KeyCode.up)        -> List(MoveCursor.Up),
    KeyChord(KeyCode.down)      -> List(MoveCursor.Down),
    KeyChord(KeyCode.space)     -> List(Space),
    KeyChord(KeyCode.home)      -> List(MoveCursor.RowBegin),
    KeyChord(KeyCode.end)       -> List(MoveCursor.RowEnd),
    KeyChord(KeyCode.escape)    -> List(BlurCursor),
    KeyChord(KeyCode.pageUp)    -> List(Cue.Prev),
    KeyChord(KeyCode.pageDown)  -> List(Cue.Next)
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
