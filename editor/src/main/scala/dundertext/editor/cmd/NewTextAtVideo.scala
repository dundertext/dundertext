package dundertext.editor.cmd

import dundertext.editor.{TextNode, TimingNode}

object NewTextAtVideo extends CommandDescription {
  def apply() = new NewTextAtVideo
}

/**
 * Creates a new text at current video time.
 */
class NewTextAtVideo extends SubtitlingCommand {
  override def execute(): Unit = {
    val insertPos: TimingNode = buffer.findNodeAfter(player.currentTime)
    val tmn = TimingNode(player.currentTime).withId(editor.newId())
    buffer.insertBefore(tmn, insertPos)
    val txn = TextNode.empty.withId(editor.newId())
    buffer.insertBefore(txn, insertPos)
    cursor.moveTo(txn)
  }
}
