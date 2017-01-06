val AKKA_VERSION = "2.4.10"
val SCALATEST_VERSION = "3.0.0"
val JSON4S_VERSION = "3.4.1"

lazy val scalaAdSdk = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, UniversalPlugin)
  .settings(

    name := "scalaAdSdk",
    version := "1.0",
    scalaVersion := "2.11.8",

    resolvers ++= Seq(
      "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
      Resolver.bintrayRepo("hseeberger", "maven")
    ),

    libraryDependencies ++= Seq(
      //Application config
      "com.typesafe" % "config" % "1.3.0",
      
      //Logging
      "ch.qos.logback" %  "logback-classic" % "1.1.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
      "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
      
      "com.typesafe.akka" %% "akka-http-core" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-http-testkit" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-slf4j" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-testkit" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-http-experimental" % AKKA_VERSION,
      "de.heikoseeberger" %% "akka-http-json4s" % "1.10.0",
      "org.json4s" %% "json4s-jackson" % JSON4S_VERSION,
      "org.json4s" %% "json4s-ext" % JSON4S_VERSION,
      "com.typesafe.slick"%% "slick" % "3.1.1",
      "org.postgresql" % "postgresql" % "9.4.1208.jre7",
      "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
      "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "test",
      "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
      "org.mockito" % "mockito-all" % "1.10.19" % "test",
      "com.codacy" %% "scala-consul" % "1.1.0"
    ),

    maintainer in Docker := "Emil Dafinov <emil.dafinov@gmail.com>",

    javaOptions in Universal ++= Seq(
      //Set the url for the application configuration
      "-Dconfig.url=http://consul:8500/v1/kv/web/dev/config?raw"
    )
  )
