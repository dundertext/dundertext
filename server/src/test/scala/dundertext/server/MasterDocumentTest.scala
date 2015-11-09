package dundertext.server

import dundertext.data._
import dundertext.editor._
import org.junit.Assert._
import org.junit.Test

class MasterDocumentTest {

  @Test
  def should_start_empty(): Unit = {
    val d = new MasterDocument
    assertTrue(d.buffer.isEmpty)
  }

  @Test
  def should_handle_single_user_on_empty_document(): Unit = {
    val d = new MasterDocument
    d.handle(AddTimingPatch("1", "START", Time(5200)))
    d.handle(AddTextPatch("2", "1"))
    d.handle(TextPatch("2", "\n", "I\n"))
    d.handle(TextPatch("2", "I\n", "I am testing\n"))

    val doc: Document = d.buffer.build()
    assertEquals(4, doc.entries.size)
    assertEquals(5200, doc.entries(1).asInstanceOf[Timing].value.millis)
    assertEquals("I am testing\n", doc.entries(2).asInstanceOf[Text].text)
  }

  @Test
  def should_diff_match_patch_when_conflicting_changed_by_other(): Unit = {
    // given
    val d = new MasterDocument
    d.handle(AddTextPatch("1", "START"))
    d.handle(TextPatch("1", "\n", "Green apples\n"))
    var doc: Document = d.buffer.build()
    assertEquals(3, doc.entries.size)
    assertEquals("Green apples\n", doc.entries(1).asInstanceOf[Text].text)

    // and changed by other (green -> red)
    val tn: TextNode = d.buffer.getTextNodeById("1")
    tn.firstRow.set(Row.of("Red apples"))

    // when I change fruit
    d.handle(TextPatch("1", "Green apples\n", "Green oranges\n"))

    // then the result is changed fruit and changed color
    doc = d.buffer.build()
    assertEquals("Red oranges\n", doc.entries(1).asInstanceOf[Text].text)
  }
}
