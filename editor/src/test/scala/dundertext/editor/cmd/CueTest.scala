package dundertext.editor.cmd

import dundertext.data.Time
import org.junit.Assert._
import org.junit.Test

class CueTest extends CommandTestBase {

  @Test
  def should_cue_prev(): Unit = {
    implicit val editor = given("""
      10
      Första
      20
      Andra╎
    """)
    player.currentTime = Time(20)

    // when
    def cmd = new Cue.Prev
    editor.execute(cmd)

    // then
    assertEquals(10, editor.player.currentTime.millis)
    assertRow("╎Första")
  }

  @Test
  def should_cue_next(): Unit = {
    implicit val editor = given("""
      10
      Förs╎ta
      20
      Andra
    """)
    player.currentTime = Time(15)

    // when
    def cmd = new Cue.Next
    editor.execute(cmd)

    // then
    assertEquals(20, editor.player.currentTime.millis)
    assertRow("╎Andra")
  }
}
