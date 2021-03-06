package dundertext.editor.cmd

import dundertext.editor.{DocumentBuffer, Editor}
import org.junit.Test
import org.junit.Assert._

class NewTextTest extends CommandTestBase {

  @Test
  def should_create_new_empty_text_on_empty_buffer(): Unit = {
    val editor: Editor = emptyEditor

    val cmd = new NewText
    editor.execute(cmd)

    assertEquals(1, editor.buffer.length)
    assertEquals("", editor.buffer.firstText.text.trim)
  }

  @Test
  def should_add_new_text_after_current(): Unit = {
    val buffer = DocumentBuffer.fromText("First")
    val editor: Editor = Editor(buffer)
    editor.cursor.moveTo(buffer.firstText.lastRow)

    val cmd = new NewText
    editor.execute(cmd)

    assertEquals(2, editor.buffer.length)
  }
}
