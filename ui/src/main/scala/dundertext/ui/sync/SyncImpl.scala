package dundertext.ui.sync

import dundertext.editor._
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js

class SyncImpl extends Sync {
  var activeRequest: Future[XMLHttpRequest] = _

  // init
  js.timers.setInterval(500)(sync _)
  // end-init

  override def sendPatches(patches: List[DocumentPatch]): Unit = {
    println("POST")
    val ps: String = patches.map(_.serialize).mkString("\n")
    activeRequest = Ajax("PATCH", "/api/document/ABC", ps, 0, Map.empty, false, "")
    activeRequest onComplete { _ =>
      activeRequest = null
      receivePatches(patches)
    }
  }

}
