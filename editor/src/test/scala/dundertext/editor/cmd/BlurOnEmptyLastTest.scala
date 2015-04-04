package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class BlurOnEmptyLastTest extends CommandTestBase {

  @Test
  def should_remove_empty_row_and_blur(): Unit = {
    implicit val editor = given("""
      Rad 1
      Rad 2
      ╎
    """)

    // when
    def cmd = new BlurOnEmptyLast
    editor.execute(cmd)

    // then
    assertEquals(2, editor.buffer.firstSubtitle.rowCount)
    assertEquals("Rad 1", editor.buffer.firstSubtitle.firstRow.text)
    assertEquals("Rad 2", editor.buffer.firstSubtitle.lastRow.text)
    assertEquals("", editor.cursor.toString)
  }
}
