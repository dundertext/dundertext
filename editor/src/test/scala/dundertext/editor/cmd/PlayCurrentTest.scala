package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor.Editor
import org.junit.Assert._
import org.junit.Test

class PlayCurrentTest extends CommandTestBase {

  @Test
  def should_cue_start_when_empty_buffer(): Unit = {
    // given
    val editor: Editor = emptyEditor
    player.currentTime = Time(500)

    // when
    val cmd = new PlayCurrent
    editor.execute(cmd)

    // then
    assertEquals(0, player.currentTime.millis)
    assertEquals(Time.End, player.untilTime)
  }
}
