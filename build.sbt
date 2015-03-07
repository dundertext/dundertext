organization := "dundertext"

name := "Dundertext"

version in ThisBuild := "1.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.6"

crossPaths in ThisBuild := false

lazy val `data` = project

lazy val `editor` = project.dependsOn(`data`)

lazy val `ui` = project.dependsOn(`editor`)

incOptions := incOptions.value.withNameHashing(true)
