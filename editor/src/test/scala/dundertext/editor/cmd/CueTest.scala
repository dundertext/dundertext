package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor.Player
import org.junit.Test
import org.junit.Assert._

class CueTest extends CommandTestBase {

  class MockPlayer extends Player {
    var currentTime: Time = _
    def cue(time: Time): Unit = {
      currentTime = time
    }
  }

  @Test
  def should_cue_prev(): Unit = {
    implicit val editor = given("""
      10
      Första
      20
      Andra╎
    """)
    val player = new MockPlayer
    editor.player = player
    player.currentTime = Time(20)

    // when
    def cmd = new Cue
    editor.execute(cmd)

    // then
    assertEquals(10, editor.player.currentTime.millis)
    assertRow("╎Första")
  }

}
