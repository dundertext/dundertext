package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor._
import org.junit.Assert._

abstract class CommandTestBase {

  class MockPlayer extends Player {
    var currentTime: Time = _
    def cue(time: Time): Unit = {
      currentTime = time
    }
  }

  val player = new MockPlayer

  def emptyEditor: Editor = {
    val buffer = DocumentBuffer.empty
    val editor = Editor(buffer)
    editor.player = player
    editor
  }

  def subtitleWithSingleRow: Editor = {
    val buffer = DocumentBuffer.fromText("First row.")
    assertEquals(1, buffer.firstText.rowCount)
    assertEquals("First row.\n\n", buffer.toString)
    val editor = Editor(buffer)
    editor.focusBeginning()
    editor
  }

  def given(document: String): Editor = {
    val buffer = DocumentBuffer.empty
    val editor = Editor(buffer)
    editor.player = player

    var textNode = TextNode.empty
    def finishNode(): Unit = {
      if (textNode.hasText) buffer.append(textNode)
      textNode = TextNode.empty
    }

    for (line <- document.stripMargin.trim.lines.map(_.trim)) {
      if (line.isEmpty) {
        finishNode()
      } else if (line.charAt(0).isDigit) {
        finishNode()
        buffer.append(TimingNode(Time(line.toInt)))
      } else {
        val cursor = line.indexOf('╎')
        textNode.append(line.replace("╎",""))
        if (cursor != -1) {
          editor.cursor.moveTo(textNode.lastRow, cursor)
        }
      }
    }
    finishNode()

    editor
  }

  def assertRow(expected: String)(implicit editor: Editor): Unit = {
    val actual = new StringBuilder(editor.cursor.row.text)
    actual.insert(editor.cursor.pos, '╎')
    assertEquals(expected, actual.result())
  }
}
