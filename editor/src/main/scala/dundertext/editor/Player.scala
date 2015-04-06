package dundertext.editor

import dundertext.data.Time

trait Player {
  def cue(time: Time): Unit
  def currentTime: Time

  def isPaused: Boolean
  def play(): Unit
  def pause(): Unit
  def cueStart(): Unit
  def cueEnd(): Unit
  def seek(offsetMillis: Int): Unit
}
