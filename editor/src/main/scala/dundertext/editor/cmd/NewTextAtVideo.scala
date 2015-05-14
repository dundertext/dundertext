package dundertext.editor.cmd

import dundertext.editor.{AddTimingPatch, AddTextPatch, TimingNode, TextNode}

object NewTextAtVideo extends CommandDescription {
  def apply() = new NewTextAtVideo
}

/**
 * Creates a new text at current video time.
 */
class NewTextAtVideo extends SubtitlingCommand {
  override def execute(): Unit = {
    val insertPos: TimingNode = buffer.findNodeAfter(player.currentTime)

    val p1 = AddTimingPatch(editor.newId(), insertPos.id, player.currentTime)
    val p2 = AddTextPatch(editor.newId(), insertPos.id)
    patch = List(p1, p2)
    p1.apply(buffer)
    p2.apply(buffer)

    val t: TextNode = buffer.getTextNodeById(p2.id)
    cursor.moveTo(t)
  }
}
