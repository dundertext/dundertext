package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class TypeTextTest extends CommandTestBase {
  val editor = subtitleWithSingleRow
  val cmd = new TypeText("typed")

  @Test
  def should_type_text_at_start_of_row(): Unit = {
    editor.execute(cmd)
    assertEquals("typedFirst row.", editor.buffer.firstText.text.trim)
  }

  @Test
  def should_type_text_at_end_of_row(): Unit = {
    editor.cursor.moveRowEnd()
    editor.execute(cmd)
    assertEquals("First row.typed", editor.buffer.firstText.text.trim)
  }

  @Test
  def should_increase_row_length_when_typing(): Unit = {
    assertEquals(10, editor.cursor.row.length)
    editor.cursor.moveRight(1)
    editor.execute(cmd)
    assertEquals(15, editor.cursor.row.length)
  }
}


