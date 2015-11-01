package dundertext.format

import java.nio.charset.Charset

import dundertext.data._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object SrtFormat {
  private final val CP_1252 = Charset.forName("cp1252")

  def readString(input: String): Document = {
    val srt: Srt.File = Srt.parse(input)
    convert(srt)
  }

  def writeString(doc: Document): String = {
    val srt: Srt.File = convert(doc)
    srt.write()
  }

  def readBytes(bs: Array[Byte]): Document = {
    readString(new String(bs, CP_1252))
  }

  def writeBytes(doc: Document): Array[Byte] = {
    writeString(doc).getBytes(CP_1252)
  }

  def convert(srt: Srt.File): Document = {
    def toRow(s: String): Row =
      Row(List(Span(s)))

    def toTime(tc: String): Time =
      Time.parse(tc.replace(',', '.'))

    val b = List.newBuilder[Entry]
    for (e <- srt.entries if e.isComplete) {
      b += Timing("", toTime(e.tcIn))
      b += Text("", e.text.toList map toRow)
      b += Timing("", toTime(e.tcOut))
    }

    Document.forEntries(b.result())
  }

  def convert(doc: Document): Srt.File = {
    val f = new Srt.File()
    var count = 0

    for (d: DisplayedText <- doc.displayed) {
      count += 1
      val e = new Srt.Entry()
      e.number = count.toString
      for (r <- d.text.rows)
        e.text += r.text
      val tcIn = d.in.formatLong.replace('.', ',')
      val tcOut = d.out.formatLong.replace('.', ',')
      e.tc = s"$tcIn --> $tcOut"
      f.entries += e
    }

    f
  }
}

object Srt {
  class File {
    val entries = ArrayBuffer[Entry]()

    def write(): String = {
      val sb = new StringBuilder()
      for (e <- entries if e.isComplete) {
        e.write(sb)
      }
      sb.result()
    }
  }

  class Entry {
    var number: String = _
    var tc: String = _
    var text = ListBuffer[String]()

    def isComplete: Boolean =
      (tc ne null) && text.nonEmpty

    def tcIn: String = tc.substring(0,12)
    def tcOut: String = tc.substring(17,29)

    def parse(row: String): Unit = {
      if (number eq null) number = row
      else if (tc eq null) tc = row
      else text += row
    }

    def write(sb: StringBuilder): Unit = {
      sb.append(number).append("\r\n")
      sb.append(tc).append("\r\n")
      for (t <- text)
        sb.append(t).append("\r\n")
      sb.append("\r\n")
    }
  }

  def parse(srt: String): Srt.File = {
    val lines = srt.lines.map(_.trim)
    val result = new Srt.File()
    var e: Srt.Entry = null

    def next(): Unit = {
      e = new Srt.Entry()
      result.entries += e
    }

    next()

    for (l <- lines)
      if (l.isEmpty) next() else e.parse(l)

    result
  }
}
