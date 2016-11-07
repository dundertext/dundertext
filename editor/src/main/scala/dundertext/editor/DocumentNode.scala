package dundertext.editor

import dundertext.data.{Entry, Time}

abstract class DocumentNode {
  def build(): Entry

  var id: String = _
  var prev: DocumentNode = _
  var next: DocumentNode = _

  var pendingSyncId: String = _

  def prevTime: TimingNode = prev match {
    case tmn: TimingNode => tmn
    case txn: TextNode => txn.prevTime
  }

  def nextTime: TimingNode = next match {
    case tmn: TimingNode => tmn
    case txn: TextNode => txn.nextTime
  }

  def prevText: TextNode = prev match {
    case tmn: TimingNode if tmn.time == Time.Start => null
    case tmn: TimingNode => prev.prevText
    case txn: TextNode => txn
  }

  def nextText: TextNode = next match {
    case tmn: TimingNode if tmn.time == Time.End => null
    case tmn: TimingNode => next.nextText
    case txn: TextNode => txn
  }

  def withId(id: String): this.type = {
    this.id = id
    this
  }
}
