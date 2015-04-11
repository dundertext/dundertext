package dundertext.ui

import scalatags.Text.all._

object MainLayout {

  def page =
    body (
        input (id:="blur"),
        h1 ("Dundertext"),

        div (id:="videos",
            table (
                tr (

                )
            )
        ),

        div (id:="timelines",
            canvas()
        ),

        div (id:="editors",
            table (
                tr (

                )
            )
        ),

        footer (
            span(cls:="version",
                "Dundertext v 0.2"
            ),
            span(id:="status", "Started")
        )
    )
}
