package dundertext.data

import org.junit.Test
import org.junit.Assert._

class TimeTest {

  @Test
  def zero: Unit = {
    val t = Time(0)
    assertEquals(0, t.millis)
  }

  @Test
  def formatted_with_three_decimals: Unit = {
    val t = Time(0)
    assertEquals("00:00:00.000", t.toString)
  }
}
