package dundertext.ui

import dundertext.ui.editor.{EditorPresenter, EditorsPanel}
import dundertext.ui.keyboard.GlobalKeyboardHandler
import dundertext.ui.sync.SyncImpl
import dundertext.ui.timeline.TimelinePanel
import dundertext.ui.video.{VideoPlayerPresenter, VideosPanel}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.JSApp

object DundertextJsApp extends JSApp {
  def main(): Unit = {
    println("Starting Dundertext")

    dom.document.body.innerHTML = MainLayout.page

    val videosPanel = new VideosPanel(dom.document.querySelector("#videos tr").asInstanceOf[html.TableRow])
    val keyboard = new GlobalKeyboardHandler
    new VideoPlayerPresenter(keyboard, videosPanel.left)
    val sync = new SyncImpl()

    val editorsPanel = new EditorsPanel(dom.document.querySelector("#editors tr").asInstanceOf[html.TableRow])
    val editorPresenter = new EditorPresenter(keyboard, sync, editorsPanel.left, videosPanel.left.display, videosPanel.left.player)

    new TimelinePanel(dom.document.querySelector("#timelines canvas").asInstanceOf[html.Canvas],
      videosPanel.left.player, editorPresenter.editor)
  }
}
