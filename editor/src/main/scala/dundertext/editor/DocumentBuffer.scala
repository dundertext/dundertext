package dundertext.editor

import dundertext.data.Time

import scala.annotation.tailrec
import scala.collection.mutable

class DocumentBuffer private () {
  val entries: mutable.Buffer[DocumentNode] =
    mutable.Buffer(TimingNode(Time.Start), TimingNode(Time.End))

  def insertBefore(node: DocumentNode, pos: DocumentNode): Unit = {
    val idx = entries.indexOf(pos)
    entries.insert(idx, node)
  }

  def firstText: TextNode =
    entries.head.nextText

  def lastText: TextNode =
    entries.last.prevText

  def asText = {
    val sb = new StringBuilder
    for (i <- 1 to entries.length - 2) {
      val e = entries(i)
      sb.append(e.toString).append('\n')
    }
    sb.result()
  }

  def append(s: String): this.type = {
    val t = TextNode.empty
    t.append(s)
    append(t)
  }

  def append(t: Time): this.type = {
    append(TimingNode(t))
  }

  def append(t: DocumentNode): this.type = {
    entries.insert(entries.size - 1, t)
    relink()
    this
  }

  def relink(): Unit = {
    var prev: DocumentNode = null
    var count = 0
    for (e <- entries) {
      e match {
        case t: TextNode =>
          count += 1
          t.nr = count
        case _ =>
      }
      if (prev ne null)
        prev.next = e
      e.prev = prev
      prev = e
    }
  }

  def length: Int = {
    val lt = lastText
    if (lt == null) 0 else lt.nr
  }

  def isEmpty: Boolean =
    entries.length == 2

  def findNodeAfter(time: Time): TimingNode = {
    @tailrec def findAfter(n: DocumentNode): TimingNode = n match {
      case null => null
      case tn: TimingNode if tn.time.isAfter(time) => tn
      case _ => findAfter(n.next)
    }
    if (isEmpty) null else findAfter(entries.head)
  }

  def findTextNodeAt(time: Time): TextNode = {
    val after = findNodeAfter(time)
    after.prevText
  }

  override def toString = asText
}

object DocumentBuffer {
  def fromText(txt: String): DocumentBuffer = {
    val b = new DocumentBuffer
    b.append(txt)
    b.relink()
    b
  }

  def empty = {
    val b = new DocumentBuffer
    b.relink()
    b
  }
}
