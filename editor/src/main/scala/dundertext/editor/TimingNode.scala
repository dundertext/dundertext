package dundertext.editor

import dundertext.data.{Time, Timing}

class TimingNode private() extends DocumentNode {
  var time: Time = _

  var synced: Time = _

  def sync(): (Time, Time) = {
    val before = synced
    synced = time
    (before, synced)
  }

  def isEndNode: Boolean =
    (prev == null) || (next == null)

  def build(): Timing =
    Timing(id, time)

  override def toString: String =
    time.formatShort
}

object TimingNode {
  def apply(t: Time) = {
    val tn = new TimingNode
    tn.time = t
    tn
  }

  def apply(t: Timing) = {
    val tn = new TimingNode
    tn.id = t.id
    tn.time = t.value
    tn
  }
}
