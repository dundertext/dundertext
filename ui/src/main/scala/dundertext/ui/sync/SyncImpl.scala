package dundertext.ui.sync

import dundertext.editor._
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.ext.Ajax
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

import scala.concurrent.Future

class SyncImpl extends Sync {
  var activeRequest: Future[XMLHttpRequest] = _

  // init
  dom.setInterval(timedSync _, 500)
  // end-init

  override def sendPatches(patches: List[DocumentPatch]): Unit = {
    println("POST")
    val ps: String = patches.map(_.serialize).mkString("\n")
    activeRequest = Ajax.apply("PATCH", "/api/document/ABC", ps, 0, Map.empty, false, "")
    activeRequest onComplete { _ =>
      activeRequest = null
      receivePatches(patches)
    }
  }

}
