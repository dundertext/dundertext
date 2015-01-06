package dundertext.editor

import scala.collection.mutable.ArrayBuffer

class DocumentBuffer {
  def firstSubtitle: TextNode = entries(0).asInstanceOf[TextNode]

  def asText = {
    val sb = new StringBuilder
    for (e <- entries)
      e.asText(sb)
    sb.result()
  }

  val entries = ArrayBuffer[DocumentNode]()

  def append(s: String): this.type = {
    val t = new TextNode
    t.append(s)
    entries += t
    this
  }

  def relink(): Unit = {
    var prev: DocumentNode = null
    for (e <- entries) {
      if (prev ne null)
        prev.next = e
      e.prev = prev
      prev = e
    }
  }

  def length: Int =
    entries.length

  override def toString = asText
}

object DocumentBuffer {
  def fromText(txt: String): DocumentBuffer = {
    val b = new DocumentBuffer
    b.append(txt)
    b.relink()
    b
  }
}
