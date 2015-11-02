package dundertext.ui

object MainLayout {
  def page: String =
    "<body>" +
        "<input id='blur'></input>" +
        "<h1>Dundertext</h1>" +
        "<div id='videos'>" +
            "<table>" +
                "<tr></tr>" +
            "</table>" +
        "</div>" +
        "" +
        "<div id='timelines'>" +
            "<canvas></canvas>" +
        "</div>" +
        "" +
        "<div id='editors'>" +
            "<table>" +
                "<tr></tr>" +
            "</table>" +
        "</div>" +
        "" +
        "<footer>" +
            "<span class='version'>Dundertext v 0.2</span>" +
            "<span id='status'>Started</span>" +
        "</footer>" +
        "" +
    "</body>"
}
