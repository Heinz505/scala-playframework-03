name := "hjecinco-08162024"

version := "0.1"

scalaVersion := "2.13.14"

// Use a version that is known to exist and is compatible with Scala 2.13
libraryDependencies += "org.apache.pekko" %% "pekko-actor" % "2.7.0" // Adjust this version if necessary

// Adding additional repositories if necessary
resolvers += "Apache Snapshots" at "https://repository.apache.org/snapshots/"