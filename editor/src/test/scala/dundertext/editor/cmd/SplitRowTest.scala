package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class SplitRowTest extends CommandTestBase {

  @Test
  def should_split_row(): Unit = {
    implicit val editor = given("""
      Abcdefgh ij╎klm
    """)

    // when
    def cmd = new SplitRow
    editor.execute(cmd)

    // then
    assertRows("""
      Abcdefgh ij
      ╎klm
    """)
  }

  @Test
  def should_not_trim_splitted_second_row(): Unit = {
    implicit val editor = given("""
      Hej╎ och hå
    """)

    // when
    def cmd = new SplitRow
    editor.execute(cmd)

    // then
    assertRows("""
      Hej
      ╎ och hå
    """)
  }

  @Test
  def should_trim_splitted_prev_row(): Unit = {
    implicit val editor = given("""
      Hej ╎och hå
    """)

    // when
    def cmd = new SplitRow
    editor.execute(cmd)

    // then
    assertEquals("Hej", editor.cursor.text.firstRow.text)
  }
}
