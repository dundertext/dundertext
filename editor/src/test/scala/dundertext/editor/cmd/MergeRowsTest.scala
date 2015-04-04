package dundertext.editor.cmd

import org.junit.Assert._
import org.junit.Test

class MergeRowsTest extends CommandTestBase {

  @Test
  def should_merge_rows_when_at_beginning(): Unit = {
    implicit val editor = given("""
      Första raden
      ╎Andra raden
    """)

    // when
    def cmd = new MergeRows
    editor.execute(cmd)

    // then
    assertRow("Första raden╎Andra raden")
  }

  @Test
  def should_not_merge_empty_row(): Unit = {
    implicit val editor = given("""
      ABC
      ╎
    """)

    // when
    def cmd = new MergeRows
    editor.execute(cmd)

    // then
    assertEquals("1/2/0", editor.cursor.toString)
    assertRow("╎")
  }
}
