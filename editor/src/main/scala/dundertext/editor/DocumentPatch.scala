package dundertext.editor

import dundertext.data.Time
import DocumentPatch._

sealed trait DocumentPatch {
  def serialize: String
}

object DocumentPatch {
  val US = '\u001f'

  def unserialize(s: String): DocumentPatch = {
    s.split('\t') match {
      case Array("TX", syncId, id, prevId, source, target) =>
        TextPatch(syncId = syncId, id = id, prevId = prevId, source = source.replace(US, '\n'),
                  target = target.replace(US, '\n'))

      case Array("TM", id, prevId, t) =>
        TimingPatch(id = id, prevId = prevId, t = Time(t.toInt))

      case Array("R", id) =>
        RemovePatch(id)
    }
  }
}


case class TextPatch (
  syncId: String,
  id: String,
  prevId: String,
  source: String,
  target: String
) extends DocumentPatch {

  override def serialize: String = Array(
    "TX",
    syncId,
    id,
    prevId,
    source.replace('\n', US),
    target.replace('\n', US)
  ).mkString("\t")
}

case class TimingPatch (
  id: String,
  prevId: String,
  t: Time
) extends DocumentPatch {

  override def serialize: String = Array("TM", id, prevId, t.millis.toString).mkString("\t")
}

case class RemovePatch (
  id: String
) extends DocumentPatch {

  def apply(buffer: DocumentBuffer): Unit = {}

  override def serialize: String = Array("R", id).mkString("\t")
}
