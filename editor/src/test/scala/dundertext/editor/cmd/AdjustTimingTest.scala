package dundertext.editor.cmd

import dundertext.data.Time
import org.junit.Test
import org.junit.Assert._

class AdjustTimingTest extends CommandTestBase {

  @Test
  def should_cue_video_at_first_stroke(): Unit = {
    implicit val editor = given("""
      1000
        Första╎
      2000
        Andra
    """)
    player.currentTime = Time(1500)

    // when
    def cmd = new AdjustTiming.TenthBack
    editor.execute(cmd)

    // then
    assertEquals(1000, editor.player.currentTime.millis)
  }

  @Test
  def should_adjust_timing_one_thenth_back(): Unit = {
    implicit val editor = given("""
      1000
        Första╎
      2000
        Andra
    """)
    player.currentTime = Time(1000)

    // when
    def cmd = new AdjustTiming.TenthBack
    editor.execute(cmd)

    // then
    assertEquals(900, editor.player.currentTime.millis)
  }

  @Test
  def should_adjust_timing_one_thenth_forward(): Unit = {
    implicit val editor = given("""
      1000
        Första╎
      2000
        Andra
    """)
    player.currentTime = Time(1000)

    // when
    def cmd = new AdjustTiming.TenthForward
    editor.execute(cmd)

    // then
    assertEquals(1100, editor.player.currentTime.millis)
  }
}
