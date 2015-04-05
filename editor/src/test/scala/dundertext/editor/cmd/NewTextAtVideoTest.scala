package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor._
import org.junit.Assert._
import org.junit.Test

class NewTextAtVideoTest extends CommandTestBase {

  @Test
  def should_create_new_empty_text_at_video_time(): Unit = {
    // given
    val editor: Editor = emptyEditor
    player.currentTime = Time(0)

    // when
    val cmd = new NewTextAtVideo
    editor.execute(cmd)

    // then
    assertEquals(4, editor.buffer.entries.size)
    val firstSub = editor.buffer.firstText
    val firstTime = firstSub.prev.asInstanceOf[TimingNode]
    assertEquals("0:00.0", firstTime.time.formatShort)
  }

  @Test
  def should_insert_text_in_timing_order(): Unit = {
    implicit val editor = given("""
      10
      Hej
      30
      Trall
    """)
    player.currentTime = Time(20)

    // when
    val cmd = new NewTextAtVideo
    editor.execute(cmd)

    // then
    assertEquals(10, editor.buffer.entries(1).asInstanceOf[TimingNode].time.millis)
    assertEquals("Hej", editor.buffer.entries(2).asInstanceOf[TextNode].text.trim)
    assertEquals(20, editor.buffer.entries(3).asInstanceOf[TimingNode].time.millis)
    assertEquals("", editor.buffer.entries(4).asInstanceOf[TextNode].text.trim)
    assertEquals(30, editor.buffer.entries(5).asInstanceOf[TimingNode].time.millis)
    assertEquals("Trall", editor.buffer.entries(6).asInstanceOf[TextNode].text.trim)
  }
}
