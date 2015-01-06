package dundertext.editor

abstract class DocumentNode {
  var prev: DocumentNode = _
  var next: DocumentNode = _
  def asText(builder: StringBuilder): Unit
}
