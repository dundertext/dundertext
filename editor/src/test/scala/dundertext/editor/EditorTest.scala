package dundertext.editor

import dundertext.data.Time
import dundertext.editor.cmd.{CommandTestBase, NewTextAtVideo}
import org.junit.Test
import org.junit.Assert._

class EditorTest extends CommandTestBase {

  class MockPlayer extends Player {
    override def currentTime: Time = {
      Time(5000)
    }
    override def cue(time: Time) = {}
  }

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
    editor.player = new MockPlayer
    editor.placeCursorAtVideo()

    // then
    assertEquals("B", editor.cursor.text.text.trim)
  }
}
