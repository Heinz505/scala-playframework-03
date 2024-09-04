name := """hjecinco-09042024-review"""
organization := "pika.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

libraryDependencies ++= Seq(
  caffeine,  
  ws 
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "pika.com.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "pika.com.binders._"
