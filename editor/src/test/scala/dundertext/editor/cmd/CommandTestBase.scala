package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor.{TimingNode, TextNode, DocumentBuffer, Editor}
import org.junit.Assert._

abstract class CommandTestBase {

  def emptyEditor: Editor = {
    val buffer = DocumentBuffer.empty
    Editor(buffer)
  }

  def subtitleWithSingleRow: Editor = {
    val buffer = DocumentBuffer.fromText("First row.")
    assertEquals(1, buffer.firstSubtitle.rowCount)
    assertEquals("First row.\n\n", buffer.toString)
    val editor = Editor(buffer)
    editor.focusBeginning()
    editor
  }

  def given(document: String): Editor = {
    val buffer = DocumentBuffer.empty
    val editor = Editor(buffer)

    var textNode = new TextNode
    buffer.append(textNode)

    for (line <- document.stripMargin.trim.lines.map(_.trim)) {
      if (line.isEmpty && textNode.hasText) {
        textNode = new TextNode
        buffer.append(textNode)
      } else if (line.charAt(0).isDigit) {
        buffer.append(TimingNode(Time(line.toInt)))
        textNode = new TextNode
        buffer.append(textNode)
      } else {
        val cursor = line.indexOf('╎')
        textNode.append(line.replace("╎",""))
        if (cursor != -1) {
          editor.cursor.moveTo(buffer.lastSubtitle.lastRow, cursor)
        }
      }
    }

    editor
  }

  def assertRow(expected: String)(implicit editor: Editor): Unit = {
    val actual = new StringBuilder(editor.cursor.row.text)
    actual.insert(editor.cursor.pos, '╎')
    assertEquals(expected, actual.result())
  }
}
