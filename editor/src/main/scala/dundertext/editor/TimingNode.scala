package dundertext.editor

import dundertext.data.Time

class TimingNode extends DocumentNode {
  var time: Time = _

  override def asText(sb: StringBuilder): Unit = {
    sb.append(time)
    sb.append('\n')
  }

  override def toString = s"TimingNode($time)"
}

object TimingNode {
  def apply(t: Time) = {
    val tn = new TimingNode
    tn.time = t
    tn
  }
}
