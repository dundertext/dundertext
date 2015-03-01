package dundertext.ui.editor

import org.scalajs.dom
import org.scalajs.dom.html

class EditorsPanel(tr: html.TableRow) {
   val td: html.Element = dom.document.createElement("td").asInstanceOf
   tr.appendChild(td)
   val left: EditorPanel = new EditorPanel(td)
 }
