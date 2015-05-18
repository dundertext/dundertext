package dundertext.editor.cmd

import dundertext.editor.{Editor, Player}

abstract class SubtitlingCommand {
  protected var editor: Editor = _
  protected var player: Player = _

  def applies: Boolean = true
  def relinks: Boolean = true
  def execute(): Unit

  def cursor = editor.cursor
  def buffer = editor.buffer

  def link(editor: Editor, player: Player) = {
    this.editor = editor
    this.player = player
  }
}

trait CommandDescription {
  def apply(): SubtitlingCommand
  def description: String = ""
}
