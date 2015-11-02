enablePlugins(ScalaJSPlugin)

//ScalaJSKeys.persistLauncher := true
//
//ScalaJSKeys.persistLauncher in Test := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.2"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../data/src/main/scala"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../editor/src/main/scala"
