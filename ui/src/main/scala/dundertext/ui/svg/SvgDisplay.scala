package dundertext.ui.svg

import dundertext.editor.{Cursor, TextNode}
import dundertext.ui.editor.EditorHtmlFormatter
import dundertext.ui.util.Html
import org.scalajs.dom.svg

import scala.collection.mutable

class SvgDisplay(e: org.scalajs.dom.svg.Element) {
  def display(text: TextNode, cursor: Cursor): Unit = {
    if (text != null)
      displayText(text, cursor)
    else
      e.innerHTML = ""
  }

  def displayText(textNode: TextNode, cursor: Cursor): Unit = {
    def rowHtml(rowText: String): String = {
      val txt: String = if (rowText.isEmpty)
        EditorHtmlFormatter.NbSp.toString
      else
        rowText.replace(' ', EditorHtmlFormatter.NbSp)

      "<g>" +
        "<rect fill='#000000' fill-opacity='0.5'></rect>" +
        "<text x='25%' y='95%' font-family='Verdana' font-size='40' font-weight='bold' " +
              "stroke='black' fill='white' stroke-width='0.6'>" +
          Html.escapeText(txt) +
        "</text>" +
        "<line id='cursor' stroke='yellow' stroke-width='3'></line>" +
      "</g>"
    }

    val sb = new StringBuilder
    for (row <- textNode.rows) {
      sb.append(rowHtml(row.text))
    }

    e.innerHTML = sb.result()
    val rows = createRows()
    rows foreach (_.place())
    if (cursor != null)
      new SvgCursorLine(rows(cursor.row.nr - 1), cursor.pos)
  }

  private def createRows(): mutable.Buffer[SvgRowGroup] = {
    val rows = mutable.Buffer[SvgRowGroup]()
    val cn = e.childNodes
    var i = 0
    while (i < cn.length) {
      rows += new SvgRowGroup(cn.item(i).asInstanceOf[svg.Element], cn.length - i - 1)
      i += 1
    }
    rows
  }

  class SvgRowGroup(e: svg.Element, val rowno: Int) {
    val rectE = e.firstElementChild.asInstanceOf[svg.RectElement]
    val textE = rectE.nextSibling.asInstanceOf[svg.Text]
    val lineE = textE.nextSibling.asInstanceOf[svg.Line]
    val box = textE.getBBox()

    def place(): Unit = {
      rectE.setAttribute("x", (box.x - 10).toString)
      rectE.setAttribute("y", box.y.toString)
      rectE.setAttribute("width", (box.width + 20).toString)
      rectE.setAttribute("height", box.height.toString)
      e.setAttribute("transform", "translate(0, -" + rowno * (box.height + 4) + ")" )
    }
  }

  class SvgCursorLine(g: SvgRowGroup, cursorPos: Int) {
    val xPos = if (cursorPos == 0)
      g.textE.getStartPositionOfChar(cursorPos).x.toString
    else
      g.textE.getEndPositionOfChar(cursorPos-1).x.toString

    g.lineE.setAttribute("x1", xPos)
    g.lineE.setAttribute("x2", xPos)
    g.lineE.setAttribute("y1", g.box.y.toString)
    g.lineE.setAttribute("y2", (g.box.y + g.box.height).toString)
  }
}
