package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class CursorTest {

  @Test
  def should_start_at_position_0(): Unit = {
    val c = new Cursor
    assertEquals(0, c.pos)
  }

}
