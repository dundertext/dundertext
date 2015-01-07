package dundertext.editor.cmd

import dundertext.editor.{Player, Editor}

abstract class SubtitlingCommand {
  protected var editor: Editor = _
  protected var player: Player = _

  def execute(): Unit

  def cursor = editor.cursor
  def buffer = editor.buffer

  def link(editor: Editor, player: Player) = {
    this.editor = editor
    this.player = player
  }
}
