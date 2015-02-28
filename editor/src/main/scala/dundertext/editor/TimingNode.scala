package dundertext.editor

class TimingNode extends DocumentNode {
  override def asText(sb: StringBuilder): Unit = {
    sb.append('\n')
  }
}
