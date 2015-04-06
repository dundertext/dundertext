package dundertext.data

final case class Time(millis: Int) {

  def isBefore(that: Time): Boolean = this.millis < that.millis
  def isAfter(that: Time): Boolean = this.millis > that.millis

  def parts: (Int, Int, Int, Int) = {
    val ms = millis % 1000
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (60 * 1000)) % 60
    val hours = millis / (60 * 60 * 1000)
    (hours, minutes, seconds, ms)
  }

  def formatShort: String = {
    val (hours, minutes, seconds, ms) = parts
    val tenths = ms / 100
    if (hours > 0)
      f"$hours:$minutes%02d:$seconds%02d.$tenths"
    else
      f"$minutes:$seconds%02d.$tenths"
  }

  def formatLong: String = {
    val (hours, minutes, seconds, ms) = parts
    f"$hours%02d:$minutes%02d:$seconds%02d.$ms%03d"
  }

  override def toString = formatLong
}

object Time {
  final val Start = Time(0)
  final val End = Time(Int.MaxValue)

  def fromSeconds(secs: Double): Time =
    Time((secs * 1000.0d).toInt)

  def fromSecondsRounded(secs: Double): Time =
    Time(((secs * 1000.0d).toInt / 100) * 100)
}
