package dundertext.ui.svg

import scalatags.Text.short._
import scalatags.Text.svgAttrs._
import scalatags.Text.svgTags._
import org.scalajs.dom.svg

class SvgDisplay(e: org.scalajs.dom.svg.Element) {
  def display(row1: String, row2: String, cursorPos: Int) = {
    val cursorRow: Int = 1

    def rowHtml(rowText: String): String = {
      g(
        rect(fill:="#000000", fillOpacity:="0.5"),
        text(x:="25%", y:="95%", fontFamily:="Verdana", fontSize:="40",
             fontWeight:="bold", stroke:="black", fill:="white", strokeWidth:="0.6", rowText),
        line(id:="cursor", stroke:="yellow", strokeWidth:="3")
      ).render
    }

    e.innerHTML = rowHtml(row1) + rowHtml(row2)

    val g1 = new SvgRowGroup(e.firstElementChild.asInstanceOf[svg.Element])
    val g2 = new SvgRowGroup(e.firstElementChild.nextSibling.asInstanceOf[svg.Element])
    g1.placeAbove(g2)

    if (cursorRow == 1)
      new SvgCursorLine(g1, cursorPos)
    else
      new SvgCursorLine(g2, cursorPos)
  }
}

class SvgRowGroup(e: svg.Element) {
  val rectE = e.firstElementChild.asInstanceOf[svg.RectElement]
  val textE = rectE.nextSibling.asInstanceOf[svg.Text]
  val lineE = textE.nextSibling.asInstanceOf[svg.Line]
  val box = textE.getBBox()
  rectE.setAttribute("x", (box.x - 10).toString)
  rectE.setAttribute("y", box.y.toString)
  rectE.setAttribute("width", (box.width + 20).toString)
  rectE.setAttribute("height", box.height.toString)

  def placeAbove(other: SvgRowGroup) {
    e.setAttribute("transform", "translate(0, -" + (other.box.height + 4) + ")" )
  }
}

class SvgCursorLine(g: SvgRowGroup, cursorPos: Int) {
  val xPos = g.textE.getStartPositionOfChar(cursorPos).x.toString
  g.lineE.setAttribute("x1", xPos)
  g.lineE.setAttribute("x2", xPos)
  g.lineE.setAttribute("y1", g.box.y.toString)
  g.lineE.setAttribute("y2", (g.box.y + g.box.height).toString)
}
