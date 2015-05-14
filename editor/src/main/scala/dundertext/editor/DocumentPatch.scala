package dundertext.editor

import dundertext.data.Time

trait DocumentPatch {
  def apply(buffer: DocumentBuffer): Unit
  def serialize: String
}

object DocumentPatch {
  def unserialize(s: String): DocumentPatch = {
    s.split('\t') match {
      case Array("T", id, text) => TextPatch(id, text)
      case Array("ATX", id, beforeId) => AddTextPatch(id, beforeId)
      case Array("ATM", id, beforeId, t) => AddTimingPatch(id, beforeId, Time(t.toInt))
      case Array("R", id) => RemovePatch(id)
    }
  }
}

case class TextPatch (
  id: String,
  text: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val tn: TextNode = buffer.getTextNodeById(id)
    tn.rows.clear()
    tn.append(text)
  }

  override def serialize: String = Array("T", id, text).mkString("\t")
}

case class AddTextPatch (
  id: String,
  beforeId: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val insertPos: DocumentNode = buffer.getNodeById(beforeId)
    val t = TextNode.empty.withId(id)
    buffer.insertBefore(t, insertPos)
    buffer.relink()
  }

  override def serialize: String = Array("ATX", id, beforeId).mkString("\t")
}

case class AddTimingPatch (
  id: String,
  beforeId: String,
  t: Time
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val insertPos: DocumentNode = buffer.getNodeById(beforeId)
    val tn = TimingNode(t).withId(id)
    buffer.insertBefore(tn, insertPos)
    buffer.relink()
  }

  override def serialize: String = Array("ATM", id, beforeId, t.millis.toString).mkString("\t")
}

case class RemovePatch (
  id: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {}

  override def serialize: String = Array("R", id).mkString("\t")
}
