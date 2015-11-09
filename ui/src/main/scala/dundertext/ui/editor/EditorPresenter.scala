package dundertext.ui.editor

import dundertext.editor._
import dundertext.editor.cmd.VideoPlayerCommand._
import dundertext.editor.cmd._
import dundertext.ui.keyboard.{KeyChord, KeyCodes, Keyboard, KeyboardListener}
import dundertext.ui.svg.SvgDisplay
import org.scalajs.dom
import org.scalajs.dom.ext.KeyCode
import org.scalajs.dom.html
import org.scalajs.dom.raw.Selection

import scala.collection.breakOut

class EditorPresenter(
    keyboard: Keyboard,
    sync: Sync,
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
  sync.setEditor(editor)
  // end init

  override def onKeyPress(char: Char): Boolean = {
    if (!editor.cursor.isAtText)
      editor.execute(new NewTextAtVideo)

    editor.execute(new TypeText(char.toString))
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

  private val keysToCommands: Map[KeyChord, List[CommandDescription]] = {
    import KeyCode._
    import KeyCodes._
    Map(
      KeyChord(Enter)     -> List(AddRow, BlurOnEmptyLast, SplitRow),
      KeyChord(Backspace) -> List(DeleteChar.Left, MergeRows, DeleteRow),
      KeyChord(Delete)    -> List(DeleteChar.Right),
      KeyChord(Left)      -> List(MoveCursor.Left),
      KeyChord(Right)     -> List(MoveCursor.Right),
      KeyChord(Up)        -> List(MoveCursor.Up),
      KeyChord(Down)      -> List(MoveCursor.Down),
      KeyChord(Space)     -> List(AddSpace, NoSpace, NewTextAtVideo),
      KeyChord(Home)      -> List(MoveCursor.RowBegin),
      KeyChord(End)       -> List(MoveCursor.RowEnd),
      KeyChord(Escape)    -> List(BlurCursor),
      KeyChord(PageUp)    -> List(Cue.Prev),
      KeyChord(PageDown)  -> List(Cue.Next),
      KeyChord(F1)        -> List(AdjustTiming.TenthBack),
      KeyChord(F2)        -> List(AdjustTiming.TenthForward),
      KeyChord(F8)        -> List(TogglePausePlay),
      KeyChord(NumPad5)   -> List(TogglePausePlay),
      KeyChord(F7)        -> List(SecondBackward),
      KeyChord(NumPad1)   -> List(SecondBackward),
      KeyChord(F9)        -> List(SecondForward),
      KeyChord(NumPad3)   -> List(SecondForward),
      KeyChord.Alt(F7)    -> List(TenthBackward),
      KeyChord(NumPad4)   -> List(TenthBackward),
      KeyChord.Alt(F9)    -> List(TenthForward),
      KeyChord(NumPad6)   -> List(TenthForward),
      KeyChord(Tab)       -> List(PlayCurrent),
      KeyChord(F10)       -> List(MarkTime)
    )
  }

  override def onKeyDown(chord: KeyChord): Boolean = {
    dom.document.getElementById("status").textContent = ""
    val cmds = keysToCommands.getOrElse(chord, Nil)
    editor.execute(cmds)
    redraw()
    cmds.nonEmpty
  }
}
