lazy val root = (project in file("."))
  .settings(
    name := "ecommerce-api",
    organization := "com.example",
    version := "1.0",
    scalaVersion := "2.13.11",
    libraryDependencies ++= Seq(
      jdbc,
      "com.typesafe.play" %% "play-json" % "2.9.4",
      "com.typesafe.play" %% "play-ws" % "2.9.4",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "org.flywaydb" % "flyway-core" % "9.15.1",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0.0" % Test,
	  "com.typesafe.play" %% "play-test" % "2.9.4" % Test
    )
  )
  libraryDependencies ++= Seq(
  
)