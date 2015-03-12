package dundertext.editor.cmd

import dundertext.editor.{DocumentBuffer, Editor}
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

    for (line <- document.stripMargin.trim.lines.map(_.trim)) {
      val cursor = line.indexOf('╎')
      buffer.append(line.replace("╎",""))
      if (cursor != -1) {
        editor.cursor.moveTo(buffer.lastSubtitle.lastRow, cursor)
      }
    }

    editor
  }
}
