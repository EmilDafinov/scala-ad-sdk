val AKKA_VERSION = "2.4.16"
val AKKA_HTTP_VERSION = "10.0.4"
val SCALATEST_VERSION = "3.0.0"
val SIGNPOST_VERSION = "1.2.1.2"
val JSON4S_VERSION = "3.5.0"

lazy val scalaAdSdk = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, UniversalPlugin)
  .settings(baseSettings:_*)
lazy val baseSettings = Seq(
  scalaVersion := "2.12.1",

  organization := "com.github.emildafinov",
  name := "scala-ad-sdk",
  version := "1.9.5-SNAPSHOT",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

  libraryDependencies ++= Seq(
    //Application config
    "com.typesafe" % "config" % "1.3.0",

    //Logging
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
    "com.typesafe.akka" %% "akka-http-core" % AKKA_HTTP_VERSION,
    "com.typesafe.akka" %% "akka-http" % AKKA_HTTP_VERSION,
    "com.typesafe.akka" %% "akka-http-testkit" % AKKA_HTTP_VERSION,
    "com.typesafe.akka" %% "akka-http-xml" % AKKA_HTTP_VERSION,
    "io.github.lhotari" %% "akka-http-health" % "1.0.7",
    "oauth.signpost" % "signpost-core" % SIGNPOST_VERSION,
    "oauth.signpost" % "signpost-commonshttp4" % SIGNPOST_VERSION,
    "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
    "org.json4s" %% "json4s-jackson" % JSON4S_VERSION,
    "org.json4s" %% "json4s-ext" % JSON4S_VERSION,
    "de.heikoseeberger" %% "akka-http-json4s" % "1.13.0",
    "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "test",
    "org.mockito" % "mockito-all" % "1.10.19" % "test",
    "com.github.tomakehurst" % "wiremock" % "2.5.1" % "test"
  ),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/EmilDafinov/scala-ad-sdk"), 
      connection = "scm:git:git@github.com:EmilDafinov/scala-ad-sdk.git"
    )
  ),
  developers := List(
    Developer(
      id = "edafinov", 
      name = "Emil Dafinov", 
      email = "emiliorodo@gmail.com", 
      url = url("https://emiliorodo.com"))
  ),
  publishArtifact in Test := false,
  
  publishTo := {
    if (isSnapshot.value || travisPrNumber.value.isDefined)
      Some("Artifactory Realm" at "https://oss.jfrog.org/artifactory/oss-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
    else
      publishTo.value //Here we are assuming that the bintray plugin does its magic
  },
  credentials := {
    if(isSnapshot.value) 
      List(Path.userHome / ".bintray" / ".artifactory").filter(_.exists).map(Credentials(_))
    else
      credentials.value
  },
  bintrayReleaseOnPublish := !isSnapshot.value
)
