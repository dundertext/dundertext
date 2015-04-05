package dundertext.ui.editor

import dundertext.editor.cmd._
import dundertext.editor._
import dundertext.ui.keyboard.{KeyChord, Keyboard, KeyboardListener}
import dundertext.ui.svg.SvgDisplay
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html
import org.scalajs.dom.raw.Selection
import scala.collection.breakOut

class EditorPresenter(
    keyboard: Keyboard,
    panel: EditorPanel,
    svgDisplay: SvgDisplay,
    player: Player
) extends KeyboardListener {

  // init
  keyboard.listen(this)
  val editor = Editor(DocumentBuffer.empty)
  editor.player = player
  panel.display("")
  dom.setInterval(redrawVideo _, 100)
  // end init

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewTextAtVideo)

    editor.execute(new TypeText(char.toString))
    editor.buffer.relink()
    redraw()

    true
  }

  private def redraw(): Unit = {
    dom.document.getElementById("status").textContent = editor.cursor.toString

    def editorHtml = new EditorHtmlFormatter(editor).format()
    panel.display(editorHtml)
    if (editor.cursor.isAtText)
      placeEditorCursor()
    else
      dom.document.getElementById("blur").asInstanceOf[html.Input].focus()

    redrawVideo()
  }

  private def redrawVideo(): Unit = {
    val playerTime = player.currentTime
    val tns: List[TextNode] = (editor.buffer.entries collect {
      case tn: TextNode if tn.display.conatins(playerTime) => tn
    })(breakOut)

    if (tns.isEmpty)
      svgDisplay.display(null, null)
    else {
      val tn = tns.head
      val c: Cursor = if (editor.cursor.isAt(tn)) editor.cursor else null
      svgDisplay.display(tn, c)
    }
  }

  private def placeEditorCursor(): Unit = {
    panel.focus()
    val selection: Selection = dom.window.getSelection()
    val cs: html.Span = panel.cursorSpan
    selection.collapse(cs.firstChild, editor.cursor.pos)
  }

  private val keysToCommands: Map[KeyChord, List[CommandDescription]] = Map(
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
}
