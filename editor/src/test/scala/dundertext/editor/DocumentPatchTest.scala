package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class DocumentPatchTest {

  @Test
  def should_serialize(): Unit = {
    val p = TextPatch("A", "START", "END", "Hoj", "Hej")
    assertEquals("TX\tA\tSTART\tEND\tHoj\tHej", p.serialize)
  }

  @Test
  def should_unserialize(): Unit = {
    val s = "TX\tA\tSTART\tEND\tHoj\tHej"
    val p = DocumentPatch.unserialize(s)
    assertEquals(TextPatch("A", "START", "END", "Hoj", "Hej"), p)
  }
}
