package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class TypeTextTest extends CommandTestBase {
  val editor = subtitleWithSingleRow
  val cmd = new TypeText("typed")

  @Test
  def when_at_start_of_row() {
    editor.execute(cmd)
    assertEquals("typedFirst row.", editor.buffer.firstSubtitle.text.trim)
  }

  @Test
  def when_at_end_of_row() {
    editor.cursor.moveRowEnd()
    editor.execute(cmd)
    assertEquals("First row.typed", editor.buffer.firstSubtitle.text.trim)
  }
}


