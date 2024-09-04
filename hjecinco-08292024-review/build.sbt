name := """hjecinco-08292024-review"""
organization := "ok.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

//scalaVersion := "3.5.0"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.0" % Test,
  "org.playframework" %% "play-slick" % "6.1.0",
  "com.h2database" % "h2" % "2.1.214",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.play" %% "play-json" % "2.10.0"  // For JSON handling
)
