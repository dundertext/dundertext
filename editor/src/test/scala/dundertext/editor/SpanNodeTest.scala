package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class SpanNodeTest {

  @Test
  def should_trim_left(): Unit = {
    // given
    val n = new SpanNode()
    n.text = " Hej   "

    // when
    n.trimLeft()

    // then
    assertEquals("Hej   ", n.text)
  }

  @Test
  def should_trim_right(): Unit = {
    // given
    val n = new SpanNode()
    n.text = " Hej   "

    // when
    n.trimRight()

    // then
    assertEquals(" Hej", n.text)
  }
}
