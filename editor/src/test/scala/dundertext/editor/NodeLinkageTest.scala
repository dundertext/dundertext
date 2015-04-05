package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class NodeLinkageTest {

  val buffer = DocumentBuffer.empty
  buffer.append("1")
  buffer.append("2")
  buffer.append("3")
  buffer.relink()

  @Test
  def prev_and_next(): Unit = {
    val zero = buffer.entries(0)
    val one = buffer.entries(1)
    val two = buffer.entries(2)
    val three = buffer.entries(3)
    val end = buffer.entries(4)
    assertEquals(null, zero.prev)
    assertEquals(zero, one.prev)
    assertEquals(two, one.next)
    assertEquals(one, two.prev)
    assertEquals(two, three.prev)
    assertEquals(end, three.next)
    assertEquals(null, end.next)
  }
}
