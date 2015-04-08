package dundertext.data

final case class DisplayedText (
  text: Text,
  in: Time,
  length: Length
) {
  val out: Time = Time(in.millis + length.millis)
  def conatins(t: Time): Boolean = {
    t.millis >= in.millis && t.millis < out.millis
  }
}

object DisplayedText {
  final val Separation = Length(160)
}
