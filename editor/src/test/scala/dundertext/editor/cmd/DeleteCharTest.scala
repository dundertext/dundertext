package dundertext.editor.cmd

import org.junit.Test
import org.junit.Assert._

class DeleteCharTest extends CommandTestBase {

  @Test
  def should_delete_left(): Unit = {
    implicit val editor = given("""
      Abcdefgh ij╎klm
    """)

    // when
    def cmd = new DeleteChar.Left
    editor.execute(cmd)

    // then
    assertRow("Abcdefgh i╎klm")
  }

  //@Test
  def should_merge_rows_when_at_beginning(): Unit = {
    implicit val editor = given("""
      Första raden
      ╎Andra raden
    """)

    // when
    def cmd = new DeleteChar.Left
    editor.execute(cmd)

    // then
    assertRow("Första raden╎Andra raden")
  }
}

