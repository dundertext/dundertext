package dundertext.ui

import dundertext.ui.editor.{EditorPresenter, EditorsPanel}
import dundertext.ui.keyboard.GlobalKeyboardHandler
import dundertext.ui.video.{VideoPlayerPresenter, VideosPanel}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.JSApp

object DundertextJsApp extends JSApp {
  def main(): Unit = {
    println("Starting Dundertext with Scala.js 0.6")

    dom.document.body.innerHTML = MainLayout.page.render

    val videosPanel = new VideosPanel(dom.document.querySelector("#videos tr").asInstanceOf[html.TableRow])
    val keyboard = new GlobalKeyboardHandler
    new VideoPlayerPresenter(keyboard, videosPanel.left)

    val editorsPanel = new EditorsPanel(dom.document.querySelector("#editors tr").asInstanceOf[html.TableRow])
    new EditorPresenter(keyboard, editorsPanel.left, videosPanel.left.display, videosPanel.left.player)
  }
}
