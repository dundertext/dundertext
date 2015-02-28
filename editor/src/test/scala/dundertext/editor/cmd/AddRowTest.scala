package dundertext.editor.cmd

import dundertext.editor.{Editor, DocumentBuffer}
import org.junit.Test
import org.junit.Assert._

class AddRowTest extends CommandTestBase {

  @Test
  def add_row_below: Unit = {
    val editor = subtitleWithSingleRow

    val cmd = new AddRow()
    editor.execute(cmd)

    assertEquals(2, editor.buffer.firstSubtitle.rowCount)
  }

  @Test
  def no_row_below_when_on_empty_row: Unit = {
    val buffer = DocumentBuffer.fromText("First")
    buffer.firstSubtitle.append("")
    val editor: Editor = Editor(buffer)
    editor.cursor.moveTo(buffer.firstSubtitle.lastRow)
    assertEquals(2, editor.buffer.firstSubtitle.rowCount)

    val cmd = new AddRow()
    editor.execute(cmd)

    assertEquals(2, editor.buffer.firstSubtitle.rowCount)
  }
}
