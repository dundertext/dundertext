scalaJSSettings

ScalaJSKeys.persistLauncher := true

ScalaJSKeys.persistLauncher in Test := false

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../data/src/main/scala"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../editor-model/src/main/scala"
