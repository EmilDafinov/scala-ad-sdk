val AKKA_VERSION = "2.4.16"
val AKKA_HTTP_VERSION = "10.0.3"
val SCALATEST_VERSION = "3.0.0"
val JSON4S_VERSION = "3.5.0"

lazy val scalaAdSdk = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, UniversalPlugin)
  .settings(

    name := "scalaAdSdk",
    version := "1.0",
    scalaVersion := "2.12.1",

    resolvers ++= Seq(
      "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      Resolver.bintrayRepo("hseeberger", "maven")
    ),

    libraryDependencies ++= Seq(
      //Application config
      "com.typesafe" % "config" % "1.3.0",

      //Logging
      "ch.qos.logback" %  "logback-classic" % "1.1.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-http-core" % "10.0.3",
      "com.typesafe.akka" %% "akka-http" % "10.0.3",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.0.3",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3",
//      "com.typesafe.akka" %% "akka-http-jackson" % "10.0.3",
      "com.typesafe.akka" %% "akka-http-xml" % "10.0.3",
//      "org.json4s" %% "json4s-jackson" % JSON4S_VERSION,
//      "org.json4s" %% "json4s-ext" % JSON4S_VERSION,
      "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
      "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "test",
      "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
      "org.mockito" % "mockito-all" % "1.10.19" % "test"
    ),

    maintainer in Docker := "Emil Dafinov <emil.dafinov@gmail.com>"
  )
