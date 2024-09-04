name := """hjecinco-08262024"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.0" % Test,
  "org.playframework" %% "play-slick" % "6.1.0",
  "org.postgresql" % "postgresql" % "42.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

  