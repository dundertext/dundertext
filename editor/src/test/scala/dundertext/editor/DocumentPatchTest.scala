package dundertext.editor

import org.junit.Test
import org.junit.Assert._


class DocumentPatchTest {

  @Test
  def should_serialize(): Unit = {
    val p = TextPatch("A", "", "Hej")
    assertEquals("T\tA\t\tHej", p.serialize)
  }

  @Test
  def should_unserialize(): Unit = {
    val s = "T\tA\t\tHej"
    val p = DocumentPatch.unserialize(s)
    assertEquals(TextPatch("A", "", "Hej"), p)
  }
}
