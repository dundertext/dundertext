organization            := "dundertext"
name                    := "Dundertext"
version in ThisBuild    := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.7"
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

lazy val `data`   = project
lazy val `editor` = project.dependsOn(`data`)
lazy val `ui`     = project.dependsOn(`editor`)
lazy val `server` = project.dependsOn(`editor`)
