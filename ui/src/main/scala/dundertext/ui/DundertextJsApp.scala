package dundertext.ui

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
    val caption = Caption(List(row))
    val captions = Captions(List(caption))
    dom.document.getElementById("output").innerHTML = captions.toString
  }
}
