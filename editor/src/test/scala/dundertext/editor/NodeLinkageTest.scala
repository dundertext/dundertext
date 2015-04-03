package dundertext.editor

import org.junit.Test
import org.junit.Assert._

class NodeLinkageTest {

  val buffer = new DocumentBuffer
  buffer.append("1")
  buffer.append("2")
  buffer.append("3")
  buffer.relink()

  @Test
  def prev_and_next(): Unit = {
    val one = buffer.entries(0)
    val two = buffer.entries(1)
    val three = buffer.entries(2)
    assertEquals(null, one.prev)
    assertEquals(two, one.next)
    assertEquals(one, two.prev)
    assertEquals(two, three.prev)
    assertEquals(null, three.next)
  }
}
