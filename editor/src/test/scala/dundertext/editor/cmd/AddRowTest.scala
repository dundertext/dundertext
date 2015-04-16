package dundertext.editor.cmd

import dundertext.editor.{Editor, DocumentBuffer}
import org.junit.Test
import org.junit.Assert._

class AddRowTest extends CommandTestBase {

  @Test
  def should_add_row_below(): Unit = {
    val editor = subtitleWithSingleRow
    editor.cursor.moveRowEnd()

    val cmd = new AddRow()
    editor.execute(cmd)

    assertEquals(2, editor.buffer.firstText.rowCount)
  }

  @Test
  def should_not_add_row_below_when_on_empty_row(): Unit = {
    val buffer = DocumentBuffer.fromText("First")
    buffer.firstText.append("")
    val editor: Editor = Editor(buffer)
    editor.cursor.moveTo(buffer.firstText.lastRow)
    assertEquals(2, editor.buffer.firstText.rowCount)

    val cmd = new AddRow()
    editor.execute(cmd)

    assertEquals(2, editor.buffer.firstText.rowCount)
  }
}
