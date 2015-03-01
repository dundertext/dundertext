package dundertext.ui

import dundertext.ui.editor.{EditorPresenter, EditorsPanel}
import dundertext.ui.keyboard.GlobalKeyboardHandler
import dundertext.ui.video.{VideoPlayerPresenter, VideosPanel}
import org.scalajs.dom

import scala.scalajs.js.JSApp

object DundertextJsApp extends JSApp {
  def main(): Unit = {
    println("Starting Dundertext with Scala.js 0.6")

    dom.document.body.innerHTML = MainLayout.page.render

    val videosPanel = new VideosPanel(dom.document.querySelector("#videos tr").asInstanceOf)
    val keyboard = new GlobalKeyboardHandler
    new VideoPlayerPresenter(keyboard, videosPanel.left)

    val editorsPanel = new EditorsPanel(dom.document.querySelector("#editors tr").asInstanceOf)
    new EditorPresenter(keyboard, editorsPanel.left)
  }
}
