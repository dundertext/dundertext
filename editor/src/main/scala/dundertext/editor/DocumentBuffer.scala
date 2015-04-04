package dundertext.editor

import dundertext.data.Time

import scala.annotation.tailrec
import scala.collection.mutable

class DocumentBuffer {

  def insertBefore(node: DocumentNode, pos: DocumentNode): Unit = {
    val idx = entries.indexOf(pos)
    entries.insert(idx, node)
  }

  val entries = mutable.Buffer[DocumentNode]()

  def firstSubtitle: TextNode = (entries collectFirst {
    case n: TextNode => n
  }).get

  def lastSubtitle: TextNode = entries.last.asInstanceOf[TextNode]

  def asText = {
    val sb = new StringBuilder
    for (e <- entries) {
      e.asText(sb)
      sb.append('\n')
    }
    sb.result()
  }

  def append(s: String): this.type = {
    val t = new TextNode
    t.append(s)
    entries += t
    relink()
    this
  }

  def append(t: DocumentNode): this.type = {
    entries += t
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

  def length: Int =
    entries.length

  def isEmpty: Boolean =
    length == 0

  def findNodeAt(time: Time): TimingNode = {
    @tailrec def findAfter(n: DocumentNode): TimingNode = n match {
      case null => null
      case tn: TimingNode if tn.time.isAfter(time) => tn
      case _ => findAfter(n.next)
    }
    if (isEmpty) null else findAfter(entries.head)
  }

  def findTextNodeAt(time: Time): TextNode = {
    val after = findNodeAt(time)
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
    b
  }
}
