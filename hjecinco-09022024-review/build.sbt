name := """hjecinco-09022024-review"""
organization := "com.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test


libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick"            % "6.1.1",
  "org.playframework" %% "play-slick-evolutions" % "6.1.1",
  "org.postgresql" % "postgresql" % "42.7.4",
  "ch.qos.logback" % "logback-classic" % "1.2.3"

)