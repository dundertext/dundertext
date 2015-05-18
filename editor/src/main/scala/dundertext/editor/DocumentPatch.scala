package dundertext.editor

import dundertext.data.Time

trait DocumentPatch {
  def apply(buffer: DocumentBuffer): Unit
  def serialize: String
}

object DocumentPatch {
  def unserialize(s: String): DocumentPatch = {
    s.split('\t') match {
      case Array("T", id, source, target) => TextPatch(id, source, target)
      case Array("ATX", id, afterId) => AddTextPatch(id, afterId)
      case Array("ATM", id, afterId, t) => AddTimingPatch(id, afterId, Time(t.toInt))
      case Array("R", id) => RemovePatch(id)
    }
  }
}

case class TextPatch (
  id: String,
  old: String,
  now: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val tn: TextNode = buffer.getTextNodeById(id)
    tn.rows.clear()
    tn.append(now)
  }

  override def serialize: String = Array("T", id, old, now).mkString("\t")
}

case class AddTextPatch (
  id: String,
  afterId: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val insertPos: DocumentNode = buffer.getNodeById(afterId)
    val t = TextNode.empty.withId(id)
    buffer.insertAfter(t, insertPos)
  }

  override def serialize: String = Array("ATX", id, afterId).mkString("\t")
}

case class AddTimingPatch (
  id: String,
  afterId: String,
  t: Time
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {
    val insertPos: DocumentNode = buffer.getNodeById(afterId)
    val tn = TimingNode(t).withId(id)
    buffer.insertAfter(tn, insertPos)
  }

  override def serialize: String = Array("ATM", id, afterId, t.millis.toString).mkString("\t")
}

case class RemovePatch (
  id: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {}

  override def serialize: String = Array("R", id).mkString("\t")
}
