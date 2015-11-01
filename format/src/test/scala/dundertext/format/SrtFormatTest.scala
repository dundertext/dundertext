package dundertext.format

import dundertext.data._
import org.junit.Test
import org.junit.Assert._

class SrtFormatTest  {

  @Test
  def should_import_empty_file(): Unit = {
    // given
    val input = ""

    // when
    val doc: Document = SrtFormat.readString(input)

    // then
    assertTrue(doc.entries.isEmpty)
  }

  @Test
  def should_import_single_subtitle(): Unit = {
    // given
    val input =
      """1
        |00:00:05,400 --> 00:00:10,160
        |This is a srt subtitle
      """.stripMargin

    // when
    val doc: Document = SrtFormat.readString(input)

    // then
    assertEquals("0:05.4", doc.entries(0).asInstanceOf[Timing].value.formatShort)
    assertEquals("This is a srt subtitle", doc.entries(1).asInstanceOf[Text].text)
    assertEquals("0:10.1", doc.entries(2).asInstanceOf[Timing].value.formatShort)
  }

  @Test
  def should_import_multiple_subtitles(): Unit = {
    // given
    val input =
      """1
        |00:00:05,400 --> 00:00:10,160
        |This is a srt subtitle
        |
        |2
        |00:00:11,000 --> 00:00:15,000
        |This is another srt subtitle
        |""".stripMargin

    // when
    val doc: Document = SrtFormat.readString(input)

    // then
    assertEquals("0:11.0", doc.entries(3).asInstanceOf[Timing].value.formatShort)
    assertEquals("This is another srt subtitle", doc.entries(4).asInstanceOf[Text].text)
    assertEquals("0:15.0", doc.entries(5).asInstanceOf[Timing].value.formatShort)
  }

  @Test
  def should_write_a_srt_file(): Unit = {
    // given
    val doc = Document.forEntries(List(
      Text("", List(Row(List(Span("Text number 1"))))),
      Timing("", Time.fromSeconds(5.0)),
      Text("", List(Row(List(Span("Text number 2"))))),
      Timing("", Time.fromSeconds(10.0)),
      Timing("", Time.fromSeconds(15.0)),
      Text("", List(Row(List(Span("Text number 3"))))),
      Timing("", Time.fromSeconds(25.0))
    ))

    // when
    val srt: String = SrtFormat.writeString(doc)

    // then
    assertEquals("""1
                   |00:00:00,000 --> 00:00:04,840
                   |Text number 1
                   |
                   |2
                   |00:00:05,000 --> 00:00:10,000
                   |Text number 2
                   |
                   |3
                   |00:00:15,000 --> 00:00:25,000
                   |Text number 3
                   |
                   |""".stripMargin, srt.replace("\r", ""))
  }
}
