package dundertext.data

import org.junit.Test
import org.junit.Assert._

class TimeTest {

  @Test
  def should_store_zero_millis(): Unit = {
    val t = Time(0)
    assertEquals(0, t.millis)
  }

  @Test
  def should_format_with_three_decimals(): Unit = {
    val t = Time(0)
    assertEquals("00:00:00.000", t.toString)
  }

  @Test
  def should_have_end_time(): Unit = {
    assertTrue(Time.End isAfter Time.Start)
  }
}
