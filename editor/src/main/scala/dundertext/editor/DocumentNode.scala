package dundertext.editor

abstract class DocumentNode {
  var prev: DocumentNode = _
  var next: DocumentNode = _
  def asText(builder: StringBuilder): Unit

  def prevTime: TimingNode = prev match {
    case tmn: TimingNode => tmn
    case txn: TextNode => txn.prevTime
  }

  def nextTime: TimingNode = next match {
    case tmn: TimingNode => tmn
    case txn: TextNode => txn.nextTime
  }

  def prevText: TextNode = prev match {
    case tmn: TimingNode => prev.prevText
    case txn: TextNode => txn
  }

  def nextText: TextNode = next match {
    case tmn: TimingNode => next.nextText
    case txn: TextNode => txn
  }
}
