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
    sync1.sync()

    assertEquals("List(TextPatch(editor1,1,START,\n,Adam\n))", sync1.patches.toString())

    var transformed: Seq[DocumentPatch] = master.handle(sync1.patches)
    sync1.patches = Nil

    assertEquals("ArrayBuffer(Timing(START,00:00:00.000), Text(1,List(Row(List(Span(Adam))))), Timing(END,596:31:23.647))", master.document.entries.toString)
    assertEquals("List(TextPatch(editor1,1,START,null,Adam\n))", transformed.toString())

    sync1.sync()

    assertTrue(sync1.patches.isEmpty)

    editor1.execute(new TypeText("Bertil"))
    sync1.receivePatches(transformed)  // should be ignored since new text is typed

    assertFalse(sync1.patches.isEmpty)  // sync should have been triggered on incomming events
    assertEquals("AdamBertil\n\n", editor1.buffer.asText) // editor should still show AdamBertil
    assertEquals("editor3", editor1.buffer.getTextNodeById("1").pendingSyncId)

    transformed = master.handle(sync1.patches)
    sync1.receivePatches(transformed)  // should now be applied since no new text is typed

    assertEquals("AdamBertil\n\n", editor1.buffer.asText)
    assertNull(editor1.buffer.getTextNodeById("1").pendingSyncId)
  }
}
