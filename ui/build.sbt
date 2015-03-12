enablePlugins(ScalaJSPlugin)

//ScalaJSKeys.persistLauncher := true
//
//ScalaJSKeys.persistLauncher in Test := false

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"

libraryDependencies += "com.lihaoyi" %%% "scalatags" % "0.4.6"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../data/src/main/scala"

unmanagedSourceDirectories in Compile += baseDirectory.value / "../editor/src/main/scala"
