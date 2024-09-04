val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tryslick",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.playframework" %% "play-slick" % "6.1.0",
      "com.h2database"    % "h2"        % "2.3.232",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
