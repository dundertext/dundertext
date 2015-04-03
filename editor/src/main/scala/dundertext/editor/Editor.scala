package dundertext.editor

import dundertext.editor.cmd.{CommandDescription, SubtitlingCommand}

import scala.annotation.tailrec

class Editor {
  var buffer: DocumentBuffer = _
  var cursor: Cursor = new Cursor
  var player: Player = _

  @tailrec
  final def execute(cds: List[CommandDescription]): Unit = cds match {
    case Nil =>
    case cd :: rest =>
      val cmd: SubtitlingCommand = cd.apply()
      cmd.link(this, player)
      if (cmd.applies) {
        println("Executing " + cmd)
        cmd.execute()
      } else
        execute(rest)
  }

  def execute(cmd: SubtitlingCommand): Unit = {
    cmd.link(this, player)
    if (cmd.applies) {
      println("Executing " + cmd)
      cmd.execute()
    }
  }

  def focusBeginning(): Unit = {
    cursor.moveTo(buffer.firstSubtitle)
  }

  def placeCursorAtVideo(): Unit = {
    val textNode = buffer.findNodeAt(player.currentTime)
    cursor.moveTo(textNode)
  }
}

object Editor {
  def apply(b: DocumentBuffer) = {
    val e = new Editor
    e.buffer = b
    //e.focusBeginning()
    e
  }
}
