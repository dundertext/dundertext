package dundertext.server

import dundertext.editor.DocumentPatch
import org.junit.Test
import org.junit.Assert._

class MasterDocumentTest {

  @Test
  def should_start_empty(): Unit = {
    val d = new MasterDocument
    assertTrue(d.buffer.isEmpty)
  }

  @Test
  def should_handle_single_user(): Unit = {
    val d = new MasterDocument
    DocumentPatch
  }

}
