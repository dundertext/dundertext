package dundertext.editor

import dundertext.editor.cmd.{TypeText, NewText}
import dundertext.server.MasterDocument
import org.junit.Test
import org.junit.Assert._
import scala.language.reflectiveCalls

class SyncTest {

  @Test
  def should_sync_one_user(): Unit = {
    val master = new MasterDocument()

    val editor1 = Editor(DocumentBuffer.empty)
    val sync1 = new Sync {
      var patches: List[DocumentPatch] = Nil
      override def sendPatches(ps: List[DocumentPatch]): Unit = {
        patches = ps
      }
    }
    sync1.setEditor(editor1)

    editor1.execute(new NewText())
    editor1.execute(new TypeText("Adam"))

    sync1.timedSync()
    assertEquals("List(AddTextPatch(null,START), TextPatch(null,\n,Adam\n))", sync1.patches.toString())
    assert(sync1.syncedBuffer.isEmpty)

    val transformed: Seq[DocumentPatch] = master.handle(sync1.patches)

    assertEquals("Adam\n\n", master.buffer.asText)
    assertEquals("List(AddTextPatch(null,START), TextPatch(null,\n,Adam\n))", transformed.toString())

    sync1.receivePatches(transformed)
    assertEquals("Adam\n\n", sync1.syncedBuffer.asText)
  }
}
