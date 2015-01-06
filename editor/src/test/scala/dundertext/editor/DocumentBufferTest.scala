package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class DocumentBufferTest {

  val buffer = new DocumentBuffer

  @Test
  def empty_at_start: Unit = {
    assertEquals(0, buffer.length)
  }

  @Test
  def append_text: Unit = {
    buffer.append("Hejsan")
    assertEquals(1, buffer.length)
    assertEquals("Hejsan\n\n", buffer.asText)
  }

  @Test
  def append_multiple_text: Unit = {
    buffer.append("Hejsan")
    buffer.append("Svejsan")
    assertEquals(2, buffer.length)
    assertEquals(
      "Hejsan\n\n" +
      "Svejsan\n\n", buffer.asText)
  }
}
