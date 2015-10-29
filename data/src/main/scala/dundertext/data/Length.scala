package dundertext.data

final case class Length(millis: Int)

object Length {
  def between(in: Time, out: Time) = {
    if (out == Time.End)
      Length(30000)
    else if (out.isAfter(in))
      Length(out.millis - in.millis)
    else
      Length(0)
  }
}
