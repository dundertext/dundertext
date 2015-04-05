package dundertext.data

final case class DisplayedText (
  text: Text,
  in: Time,
  length: Length
) {
  def conatins(t: Time): Boolean = {
    t.millis >= in.millis && t.millis < in.millis + length.millis
  }
}

object DisplayedText {
  final val Separation = Length(160)
}
