package dundertext.data

final case class Document (
  id: String,
  entries: List[Entry],
  displayed: List[DisplayedText]
)

object Document {
  val empty = forEntries(List(Timing("START", Time.Start), Timing("END", Time.End)))

  def forEntries(entries: List[Entry]): Document = {
    val b = List.newBuilder[DisplayedText]
    var in: Time = Time.Start
    var text: Text = null
    var out: Time = null
    var next: Time = null

    def complete(): Unit = {
      if (next == null) {
        b += DisplayedText(text, in, Length.between(in, out.minus(160)))
        in = out
      } else {
        b += DisplayedText(text, in, Length.between(in, out))
        in = next
      }
      text = null
      out = null
      next = null
    }

    entries foreach {
      case t: Timing if in == null    => in = t.value
      case t: Timing if in == t.value =>
      case t: Timing if text == null  => in = t.value
      case t: Timing if out == null   => out = t.value
      case t: Timing if next == null  => next = t.value
      case t: Text if text == null    => text = t
      case t: Text                    => complete(); text = t
    }

    next = Time.End
    if (out == null) out = next
    complete()

    Document("", entries, b.result())
  }
}
