name := """hjecinco-08152024"""
organization := "example.com"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.14"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "example.com.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "example.com.binders._"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.8.20",
  "com.typesafe.play" %% "play-ws" % "2.8.20",
  "com.typesafe.play" %% "play-cache" % "2.8.20",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.20",
  "com.typesafe.akka" %% "akka-stream" % "2.6.20",
  "com.google.inject" % "guice" % "5.0.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
)

dependencyOverrides ++= Set(
  "org.scala-lang.modules" %% "scala-xml" % "1.3.1"
)

enablePlugins(PlayScala)

resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
