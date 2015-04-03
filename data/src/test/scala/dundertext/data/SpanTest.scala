package dundertext.data

import org.junit.Assert._
import org.junit.Test

class SpanTest {

  val span = Span("Hej")

  @Test
  def should_split_beginning(): Unit = {
    val (l, r) = span.split(0)
    assertEquals("", l.text)
    assertEquals("Hej", r.text)
  }

  @Test
  def should_split_end(): Unit = {
    val (l, r) = span.split(3)
    assertEquals("Hej", l.text)
    assertEquals("", r.text)
  }

  @Test
  def should_split_outside(): Unit = {
    val (l, r) = span.split(100)
    assertEquals("Hej", l.text)
    assertEquals("", r.text)
  }

  @Test
  def should_split_inside(): Unit = {
    val (l, r) = span.split(1)
    assertEquals("H", l.text)
    assertEquals("ej", r.text)
  }
}
