package dundertext.editor.cmd

import org.junit.Test

class MergeRowsTest extends CommandTestBase {

  @Test
  def should_merge_rows_when_at_beginning(): Unit = {
    implicit val editor = given("""
      Första raden
      ╎Andra raden
    """)

    // when
    def cmd = new MergeRows
    editor.execute(cmd)

    // then
    assertRow("Första raden╎Andra raden")
  }
}

