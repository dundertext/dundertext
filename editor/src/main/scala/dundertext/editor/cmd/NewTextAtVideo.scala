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
    val tn = TimingNode(player.currentTime)
    buffer.append(tn)
    val t = TextNode.empty
    buffer.append(t)
    buffer.relink()
    cursor.moveTo(t)
  }
}
