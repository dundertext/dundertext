package dundertext.editor

import dundertext.data.Time

trait Player {
  def cue(time: Time): Unit
  def currentTime: Time
}
