package dundertext.editor

import dundertext.data.{Entry, Document, Time}

import scala.annotation.tailrec
import scala.collection.mutable

class DocumentBuffer private () {
  val entries: mutable.Buffer[DocumentNode] =
    mutable.Buffer(TimingNode(Time.Start).withId("START"), TimingNode(Time.End).withId("END"))

  def insertBefore(node: DocumentNode, pos: DocumentNode): Unit = {
    val idx = entries.indexOf(pos)
    entries.insert(idx, node)
  }

  def insertAfter(node: DocumentNode, pos: DocumentNode): Unit = {
    val idx = entries.indexOf(pos)
    entries.insert(1+idx, node)
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
    this
  }

  def relink(): Unit = {
    var prev: DocumentNode = null
    for (e <- entries) {
      if (prev ne null)
        prev.next = e
      e.prev = prev
      prev = e
    }

    var count = 0
    for (e <- entries) {
      e match {
        case t: TextNode =>
          count += 1
          t.nr = count
          t.recalcDisplay()
        case _ =>
      }
    }
  }

  def length: Int = {
    val lt = lastText
    if (lt == null) 0 else lt.nr
  }

  def isEmpty: Boolean =
    entries.length == 2

  def findNodeBeore(time: Time): TimingNode = {
    @tailrec def findBefore(n: DocumentNode): TimingNode = n match {
      case null => null
      case tn: TimingNode if tn.time.isBefore(time) => tn
      case _ => findBefore(n.prev)
    }
    findBefore(entries.last)
  }

  def findNodeAfter(time: Time): TimingNode = {
    @tailrec def findAfter(n: DocumentNode): TimingNode = n match {
      case null => null
      case tn: TimingNode if tn.time.isAfter(time) => tn
      case _ => findAfter(n.next)
    }
    findAfter(entries.head)
  }

  def findTextNodeAt(time: Time): TextNode = {
    val after = findNodeAfter(time)
    after.prevText
  }

  def findNodeById(id: String): Option[DocumentNode] =
    entries.find(_.id == id)

  def getNodeById(id: String): DocumentNode =
    findNodeById(id).orNull

  def getTextNodeById(id: String): TextNode =
    getNodeById(id).asInstanceOf[TextNode]

  def build(): Document = {
    val b = List.newBuilder[Entry]
    for (e <- entries) b += e.build()
    Document.forEntries(b.result())
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
