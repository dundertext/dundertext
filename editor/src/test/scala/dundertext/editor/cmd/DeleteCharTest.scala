package dundertext.editor.cmd

import org.junit.Test

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

  @Test
  def should_delete_right(): Unit = {
    implicit val editor = given("""
      Abcdefgh ij╎klm
    """)

    // when
    def cmd = new DeleteChar.Right
    editor.execute(cmd)

    // then
    assertRow("Abcdefgh ij╎lm")
  }
}
