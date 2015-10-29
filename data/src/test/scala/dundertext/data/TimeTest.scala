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
  def should_format_with_one_decimal(): Unit = {
    val t = Time(1100)
    assertEquals("0:01.1", t.formatShort)
  }

  @Test
  def should_have_end_time(): Unit = {
    assertTrue(Time.End isAfter Time.Start)
  }

  @Test
  def should_parse_from_iso(): Unit = {
    var t = Time.parse("00:00:00.000")
    assertEquals(0, t.millis)
    t = Time.parse("00:00:00.001")
    assertEquals(1, t.millis)
    t = Time.parse("00:00:01.002")
    assertEquals(1002, t.millis)
    t = Time.parse("00:01:02.123")
    assertEquals(62123, t.millis)
    t = Time.parse("1:00:00.000")
    assertEquals(3600000, t.millis)
    t = Time.parse("01:01:01.999")
    assertEquals(3661999, t.millis)
  }

  @Test
  def should_be_lossless(): Unit = {
    val t = Time.parse("23:59:59.999")
    assertEquals(86399999, t.millis)
    assertEquals("23:59:59.999", t.formatLong)
  }

  @Test
  def should_not_parse_illegal_hour(): Unit = {
    try { Time.parse("27:00:00.000"); fail() }
    catch { case e: IllegalArgumentException =>
        assert(e.getMessage contains "27 is not a valid hour")
    }
  }
}
