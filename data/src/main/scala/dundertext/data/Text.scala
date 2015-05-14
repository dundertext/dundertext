package dundertext.data

final case class Text (
  id: String,
  rows: List[Row]
) extends Entry {
  def text = rows.map(_.text).mkString("\n")
}
