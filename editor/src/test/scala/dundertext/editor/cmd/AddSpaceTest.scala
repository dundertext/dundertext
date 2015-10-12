package dundertext.editor.cmd

import org.junit.Assert._
import org.junit.Test

class AddSpaceTest extends CommandTestBase {

  @Test
  def should_insert_space(): Unit = {
    implicit val editor = given("""
      Gott med╎glass.
    """)

    // when
    def cmd = new AddSpace
    editor.execute(cmd)

    // then
    assertRow("Gott med ╎glass.")
  }

  @Test
  def should_not_allow_double_space(): Unit = {
    val editor = given("""
      Hej ╎och hå!
    """)

    // when
    def cmd = new AddSpace
    editor.execute(cmd)

    // then
    assertEquals("Hej och hå!", editor.cursor.row.text)
  }

  @Test
  def should_not_allow_double_space_when_space_already_right_of_cursor(): Unit = {
    implicit val editor = given("""
      Hej╎ och hå!
    """)

    // when
    def cmd = new AddSpace
    editor.execute(cmd)

    // then should only move cursor
    assertRow("Hej ╎och hå!")
  }

  @Test
  def should_not_allow_space_at_start_of_row(): Unit = {
    implicit val editor = given("""
      ╎Hej
    """)

    // when
    def cmd = new AddSpace
    editor.execute(cmd)

    // then
    assertEquals("Hej", editor.cursor.row.text)
  }
}
