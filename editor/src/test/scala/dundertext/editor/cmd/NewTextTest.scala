package dundertext.editor.cmd

import dundertext.editor.{DocumentBuffer, Editor}
import org.junit.Test
import org.junit.Assert._

class NewTextTest extends CommandTestBase {

  @Test
  def create_new_empty_text_on_empty_buffer: Unit = {
    val editor: Editor = emptyEditor

    val cmd = new NewText
    editor.execute(cmd)

    assertEquals(1, editor.buffer.entries.size)
    assertEquals("", editor.buffer.firstSubtitle.text.trim)
  }

  @Test
  def new_text_after_current: Unit = {
    val buffer = DocumentBuffer.fromText("First")
    val editor: Editor = Editor(buffer)
    editor.cursor.moveTo(buffer.firstSubtitle.lastRow)

    val cmd = new NewText
    editor.execute(cmd)

    assertEquals(2, editor.buffer.entries.size)
  }
}
