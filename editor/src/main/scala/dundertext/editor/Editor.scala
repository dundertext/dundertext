package dundertext.editor

import dundertext.editor.cmd.{CommandDescription, SubtitlingCommand}

import scala.annotation.tailrec

class Editor private() {
  var buffer: DocumentBuffer = _
  var cursor: Cursor = new Cursor
  var player: Player = _
  var idCounter: Int = 0

  @tailrec
  final def execute(cds: List[CommandDescription]): Unit = cds match {
    case Nil =>
    case cd :: rest =>
      val cmd: SubtitlingCommand = cd.apply()
      cmd.link(this, player)
      if (cmd.applies)
        doExecute(cmd)
      else
        execute(rest)
  }

  def execute(cmd: SubtitlingCommand): Unit = {
    cmd.link(this, player)
    if (cmd.applies)
      doExecute(cmd)
  }

  private def doExecute(cmd: SubtitlingCommand): Unit = {
    println("Executing " + cmd)
    cmd.execute()
    if (cursor.isAtText)
      cursor.text.synced = false
    if (cmd.relinks)
      buffer.relink()
  }

  def focusBeginning(): Unit = {
    cursor.moveTo(buffer.firstText)
  }

  def placeCursorAtVideo(): Unit = {
    val textNode = buffer.findTextNodeAt(player.currentTime)
    cursor.moveTo(textNode)
  }

  def newId(): String = {
    idCounter += 1
    String.valueOf(idCounter)
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
