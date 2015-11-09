package dundertext.data

import org.junit.Test
import org.junit.Assert._

class DocumentTest {

  def formatDisplayed(d: DisplayedText): String = {
    s"${d.in.formatShort} (${d.length.millis}) ${d.text.text.trim}"
  }

  @Test
  def should_calc_display_timing_for_one(): Unit = {
    // given
    val es: List[Entry] = List(
      Timing("", Time.fromSeconds(0.0)),
      Text("", List(Row(List(Span("First text"))))),
      Timing("", Time.fromSeconds(5.0))
    )

    // when
    val document: Document = Document.forEntries(es)

    // then
    assertEquals(List(
      "0:00.0 (5000) First text"
    ), document.displayed map formatDisplayed)
  }

  @Test
  def should_calc_display_timing_for_two_separated(): Unit = {
    // given
    val es: List[Entry] = List(
      Timing("", Time.fromSeconds(0.0)),
      Text("", List(Row(List(Span("First text"))))),
      Timing("", Time.fromSeconds(5.0)),

      Timing("", Time.fromSeconds(10.0)),
      Text("", List(Row(List(Span("Second text"))))),
      Timing("", Time.fromSeconds(15.0))
    )

    // when
    val document: Document = Document.forEntries(es)

    // then
    assertEquals(List(
      "0:00.0 (5000) First text",
      "0:10.0 (5000) Second text"
    ), document.displayed map formatDisplayed)
  }

  @Test
  def should_calc_display_timing_for_two_continous(): Unit = {
    // given
    val es: List[Entry] = List(
      Timing("", Time.fromSeconds(0.0)),
      Text("", List(Row(List(Span("First text"))))),
      Timing("", Time.fromSeconds(5.0)),
      Text("", List(Row(List(Span("Second text"))))),
      Timing("", Time.fromSeconds(10.0))
    )

    // when
    val document: Document = Document.forEntries(es)

    // then
    assertEquals(List(
      "0:00.0 (4840) First text",
      "0:05.0 (5000) Second text"
    ), document.displayed map formatDisplayed)
  }

  @Test
  def should_calc_display_timing_for_text_from_start(): Unit = {
    // given
    val es: List[Entry] = List(
      Text("", List(Row(List(Span("First text"))))),
      Timing("", Time.fromSeconds(5.0))
    )

    // when
    val document: Document = Document.forEntries(es)

    // then
    assertEquals(List(
      "0:00.0 (5000) First text"
    ), document.displayed map formatDisplayed)
  }

  @Test
  def should_calc_display_timing_for_text_at_end(): Unit = {
    // given
    val es: List[Entry] = List(
      Timing("", Time.fromSeconds(90.0)),
      Text("", List(Row(List(Span("Last text")))))
    )

    // when
    val document: Document = Document.forEntries(es)

    // then
    assertEquals(List(
      "1:30.0 (30000) Last text"
    ), document.displayed map formatDisplayed)
  }
}
