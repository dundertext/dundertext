package dundertext.editor

import dundertext.data.Time

class TimingNode private() extends DocumentNode {
  var time: Time = _

  def isEndNode: Boolean =
    prev == null || next == null

  override def toString: String =
    time.formatShort
}

object TimingNode {
  def apply(t: Time) = {
    val tn = new TimingNode
    tn.time = t
    tn
  }
}
