package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class MoveCursorTest extends CommandTestBase {

  @Test
  def should_move_left: Unit = {
    val editor = given("""
      Det här är en undertext på ╎en rad.
    """)
    assertEquals("1/1/27", editor.cursor.toString)

    // when
    def cmd = new MoveCursor.Left
    editor.execute(cmd)

    // then
    assertEquals("1/1/26", editor.cursor.toString)
  }

  @Test
  def should_not_move_left_when_at_start_of_row: Unit = {
    val editor = given("""
      ╎Det här är en undertext på en rad.
    """)
    assertEquals("1/1/0", editor.cursor.toString)

    // when
    def cmd = new MoveCursor.Left
    editor.execute(cmd)

    // then
    assertEquals("1/1/0", editor.cursor.toString)
  }

  @Test
  def should_move_right: Unit = {
    val editor = given("""
      Det här är en undertext på ╎en rad.
    """)

    // when
    def cmd = new MoveCursor.Right
    editor.execute(cmd)

    // then
    assertEquals("1/1/28", editor.cursor.toString)
  }

  @Test
  def should_not_move_right_when_at_end_of_row: Unit = {
    val editor = given("""
      Det här är en undertext på en rad.╎
    """)
    assertEquals("1/1/34", editor.cursor.toString)

    // when
    def cmd = new MoveCursor.Right
    editor.execute(cmd)

    // then
    assertEquals("1/1/34", editor.cursor.toString)
  }

  @Test
  def should_move_to_beginning_of_row: Unit = {
    val editor = given("""
      Det här är en undertext på ╎en rad.
    """)
    assertEquals("1/1/27", editor.cursor.toString)

    // when
    def cmd = new MoveCursor.RowBegin
    editor.execute(cmd)

    // then
    assertEquals("1/1/0", editor.cursor.toString)
  }
}
