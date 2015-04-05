package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class DocumentBufferTest {

  val buffer = DocumentBuffer.empty

  @Test
  def should_be_empty_at_start(): Unit = {
    assertEquals(0, buffer.length)
    assertTrue(buffer.isEmpty)
  }

  @Test
  def should_append_text(): Unit = {
    buffer.append("Hejsan")
    assertEquals(1, buffer.length)
    assertEquals("Hejsan\n\n", buffer.asText)
  }

  @Test
  def should_append_multiple_text(): Unit = {
    buffer.append("Hejsan")
    buffer.append("Svejsan")
    assertEquals(2, buffer.length)
    assertEquals(
      "Hejsan\n\n" +
      "Svejsan\n\n", buffer.asText)
  }
}
