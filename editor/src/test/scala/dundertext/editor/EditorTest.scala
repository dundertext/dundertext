package dundertext.editor

import dundertext.data.Time
import dundertext.editor.cmd.CommandTestBase
import org.junit.Assert._
import org.junit.Test

class EditorTest extends CommandTestBase {

  @Test
  def should_place_cursor_at_video(): Unit = {
    val editor: Editor = given(
      """
        0
          A
        2000
          B
        9000
          C
        10000
      """)
    player.currentTime = Time(5000)

    // when
    editor.placeCursorAtVideo()

    // then
    assertEquals("B", editor.cursor.text.text.trim)
  }
}
