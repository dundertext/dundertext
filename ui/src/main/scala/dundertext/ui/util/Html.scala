package dundertext.ui.util

import scala.scalajs.js.JSStringOps._
import scala.scalajs.js.RegExp

object Html {
  private final val AMP_RE = new RegExp("&", "g")
  private final val GT_RE = new RegExp(">", "g")
  private final val LT_RE = new RegExp("<", "g")
  private final val SQUOT_RE = new RegExp("\'", "g")
  private final val QUOT_RE = new RegExp("\"", "g")

  def escapeText(s: String): String = {
    var r: String = s
    if (r.jsIndexOf("&") != -1) r = r.jsReplace(AMP_RE, "&amp;")
    if (r.jsIndexOf("<") != -1) r = r.jsReplace(LT_RE, "&lt;")
    if (r.jsIndexOf(">") != -1) r = r.jsReplace(GT_RE, "&gt;")
    r
  }

  def escapeAttr(s: String): String = {
    var r: String = s
    if (r.jsIndexOf("&") != -1) r = r.jsReplace(AMP_RE, "&amp;")
    if (r.jsIndexOf("<") != -1) r = r.jsReplace(LT_RE, "&lt;")
    if (r.jsIndexOf(">") != -1) r = r.jsReplace(GT_RE, "&gt;")
    if (r.jsIndexOf("\"") != -1) r = r.jsReplace(QUOT_RE, "&quot;")
    if (r.jsIndexOf("\'") != -1) r = r.jsReplace(SQUOT_RE, "&#39;")
    r
  }
}
