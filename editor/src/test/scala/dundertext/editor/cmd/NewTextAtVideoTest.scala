package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor.{TimingNode, Player, DocumentBuffer, Editor}
import org.junit.Assert._
import org.junit.Test

class NewTextAtVideoTest extends CommandTestBase {

  class MockPlayer extends Player {

    override def currentTime: Time = {
      Time(0)
    }

    override def cue(time: Time) = {}
  }

  @Test
  def should_create_new_empty_text_at_video_time(): Unit = {
    val editor: Editor = emptyEditor
    editor.player = new MockPlayer

    val cmd = new NewTextAtVideo
    editor.execute(cmd)

    assertEquals(2, editor.buffer.entries.size)
    val firstSub = editor.buffer.firstSubtitle
    val firstTime = firstSub.prev.asInstanceOf[TimingNode]
    assertEquals("0:00.0", firstTime.time.formatShort)
  }
}
