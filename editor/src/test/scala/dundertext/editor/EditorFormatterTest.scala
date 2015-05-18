package dundertext.editor

import dundertext.data.Time
import org.junit.Test
import org.junit.Assert._

class EditorFormatterTest {

  val buffer = DocumentBuffer.empty
  val editor = Editor(buffer)

  @Test
  def should_format_for_editor(): Unit = {
    buffer.append("Ett")
    buffer.append(Time(1500))
    buffer.append("Två")
    buffer.append(Time(2000))
    buffer.relink()
    val txt: String = new EditorFormatter(editor).format()
    assertEquals(
      """  Ett
        |0:01.5
        |  Två
        |0:02.0
        |""".stripMargin,
      txt)
  }

}
