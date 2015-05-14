package dundertext.editor.cmd

import dundertext.data.Time
import dundertext.editor._
import org.junit.Assert._

abstract class CommandTestBase {

  class MockPlayer extends Player {
    var playing = false
    var currentTime: Time = _
    var untilTime: Time = _
    def cue(time: Time): Unit = {
      currentTime = time
    }

    override def isPaused = !playing
    override def play() = playing = true
    override def seek(offsetMillis: Int) = currentTime = new Time(currentTime.millis + offsetMillis)
    override def cueEnd() = ???
    override def pause() = playing = false
    override def cueStart() = ???
    override def playUntil(time: Time): Unit = {
      untilTime = time
      play()
    }
  }

  val player = new MockPlayer

  def emptyEditor: Editor = {
    val buffer = DocumentBuffer.empty
    val editor = Editor(buffer)
    editor.player = player
    editor
  }

  def subtitleWithSingleRow: Editor = {
    val buffer = DocumentBuffer.fromText("First row.")
    assertEquals(1, buffer.firstText.rowCount)
    assertEquals("First row.\n\n", buffer.toString)
    val editor = Editor(buffer)
    editor.focusBeginning()
    editor
  }

  def given(document: String): Editor = {
    val buffer = DocumentBuffer.empty
    val editor = Editor(buffer)
    editor.player = player

    var textNode = TextNode.empty.withId(editor.newId())
    def finishNode(): Unit = {
      if (textNode.hasText) buffer.append(textNode)
      textNode = TextNode.empty.withId(editor.newId())
    }

    for (line <- document.stripMargin.trim.lines.map(_.trim)) {
      if (line.isEmpty) {
        finishNode()
      } else if (line.charAt(0).isDigit) {
        finishNode()
        buffer.append(TimingNode(Time(line.toInt)).withId(editor.newId()))
      } else {
        val cursor = line.indexOf('╎')
        textNode.append(line.replace("╎",""))
        if (cursor != -1) {
          editor.cursor.moveTo(textNode.lastRow, cursor)
        }
      }
    }
    finishNode()

    editor
  }

  def assertRow(expected: String)(implicit editor: Editor): Unit = {
    val actual = new StringBuilder(editor.cursor.row.text)
    actual.insert(editor.cursor.pos, '╎')
    assertEquals(expected, actual.result())
  }

  def assertRows(expected: String)(implicit editor: Editor): Unit = {
    val e = expected.trim.lines.map(_.trim).mkString("\n")
    val actual = new TestDataFormatter(editor).format().trim.lines.map(_.trim).mkString("\n")
    assertEquals(e, actual)
  }
}

class TestDataFormatter(editor: Editor) extends EditorFormatter(editor) {
  override def writeTextAtCursor(s: String, pos: Int) = {
    sb.append(s.take(pos))
    sb.append('╎')
    sb.append(s.drop(pos))
  }
}

