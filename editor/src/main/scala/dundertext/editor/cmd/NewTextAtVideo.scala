package dundertext.editor.cmd

import dundertext.editor.{TimingNode, TextNode}

object NewTextAtVideo extends CommandDescription {
  def apply() = new NewTextAtVideo
}

/**
 * Creates a new text at current video time.
 */
class NewTextAtVideo extends SubtitlingCommand {
  override def execute(): Unit = {
    val insertPos: TimingNode = buffer.findNodeAfter(player.currentTime)

    val tn = TimingNode(player.currentTime)
    val t = TextNode.empty

    if (insertPos != null) {
      buffer.insertBefore(tn, insertPos)
      buffer.insertBefore(t, insertPos)
    } else {
      buffer.append(tn)
      buffer.append(t)
    }

    buffer.relink()
    cursor.moveTo(t)
  }
}
