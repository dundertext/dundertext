package dundertext.data

case class Captions (
  captions: List[Caption]
)

case class Caption (
  rows: List[Row]
)

case class Row (
  spans: List[Span]
)

case class Span (
  text: String
)
