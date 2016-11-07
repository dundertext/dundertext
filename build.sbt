organization            := "dundertext"
name                    := "Dundertext"
version in ThisBuild    := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.8"
crossPaths in ThisBuild   := false

incOptions := incOptions.value.withNameHashing(true)
testOptions in ThisBuild += Tests.Argument(TestFrameworks.JUnit, "+q", "-v")
scalacOptions in ThisBuild ++= Seq(
  "-Xlint",
  "-unchecked",
  "-deprecation",
  "-target:jvm-1.8",
  "-encoding", "utf-8",
  "-feature",
  "-optimise",
  "-Xfuture",
  "-Yinline-warnings"
)
libraryDependencies in ThisBuild += "com.novocode" % "junit-interface" % "0.11" % "test"

lazy val createLauncher = taskKey[Unit]("")

lazy val `data`   = project

lazy val `editor` = project.dependsOn(`data`)

lazy val `format` = project.dependsOn(`data`)

lazy val `ui`     = project
                      .dependsOn(`editor`)
                      .enablePlugins(ScalaJSPlugin)
                      .settings(
                        libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1",
                        unmanagedSourceDirectories in Compile += baseDirectory.value / "../data/src/main/scala",
                        unmanagedSourceDirectories in Compile += baseDirectory.value / "../editor/src/main/scala"
                      )

lazy val `server` = project
                      .dependsOn(`editor`, `format`)
                      .settings(
                        libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.12",
                        libraryDependencies += "com.typesafe.akka" %% "akka-http-core" % "2.4.11",
                        libraryDependencies += "com.sksamuel.diff" % "diff" % "1.1.11",

                        createLauncher := {
                          println("Writing classpath for launcher")
                          IO.write(baseDirectory.value / "target" / "cp.txt", (fullClasspath in Compile).value.map(_.data.getAbsolutePath).mkString(":"))
                        }
                      )
