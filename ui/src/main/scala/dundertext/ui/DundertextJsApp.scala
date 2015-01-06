package dundertext.ui

import dundertext.ui.keyboard.GlobalKeyboardHandler
import dundertext.ui.video.{VideoPlayerPresenter, VideoPlayerPanel}
import org.scalajs.dom.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.JSApp
import org.scalajs.dom
import js.Dynamic.{ global => g }
import dundertext.data._

object DundertextJsApp extends JSApp {
  def main(): Unit = {
    println("Starting")
    val span = Span("Hello dundertext!!")
    val row = Row(List(span))
    val caption = Text(List(row))
    val document = Document("TEST", List(caption))
    dom.document.getElementById("output").innerHTML = document.toString

    val playerPanel = new VideoPlayerPanel(dom.document.querySelector("#videos section").asInstanceOf[HTMLElement])
    val keyboard = new GlobalKeyboardHandler
    new VideoPlayerPresenter(keyboard, playerPanel)
  }
}
