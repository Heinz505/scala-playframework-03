// Project settings
lazy val root = (project in file("."))
  .settings(
    name := "ecommerce-api",
    version := "1.0",
    scalaVersion := "2.13.14",
    organization := "com.example",

    // Library dependencies
    libraryDependencies ++= Seq(
      // Play dependencies
      "com.typesafe.play" %% "play" % "2.9.4",
      "com.typesafe.play" %% "play-json" % "2.9.4",
      "com.typesafe.play" %% "play-ws" % "2.9.4",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "org.flywaydb" % "flyway-core" % "9.15.1",

      // Additional dependencies for slick
      "com.typesafe.slick" %% "slick" % "3.4.0",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.4.0",

      // For JSON serialization/deserialization
      "com.typesafe.play" %% "play-json" % "2.9.4",

      // Testing dependencies
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0.0" % Test,
      "com.typesafe.play" %% "play-test" % "2.9.4" % Test,
      "org.mockito" %% "mockito-scala" % "1.17.15" % Test,
      "org.scalatestplus" %% "mockito-3-4" % "3.2.15.0" % Test
    ),

    // Project settings
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    javaOptions += "-Dplay.http.secret.key=1234567890abcdef",
    testOptions in Test := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-oD"))
  )

// Resolvers
resolvers ++= Seq(
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Maven Central" at "https://repo1.maven.org/maven2/"
)
