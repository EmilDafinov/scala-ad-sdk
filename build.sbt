val AKKA_VERSION = "2.4.16"
val AKKA_HTTP_VERSION = "10.0.3"
val SCALATEST_VERSION = "3.0.0"

lazy val scalaAdSdk = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, UniversalPlugin)
  .settings(baseSettings:_*)
lazy val baseSettings = Seq(
  scalaVersion := "2.11.8",

  organization := "com.emiliorodo",
  name := "scala-ad-sdk",
  version := "1.0",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

  libraryDependencies ++= Seq(
    //Application config
    "com.typesafe" % "config" % "1.3.0",

    //Logging
    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
    "com.typesafe.akka" %% "akka-http-core" % "10.0.3",
    "com.typesafe.akka" %% "akka-http" % "10.0.3",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.3",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.3",
    "com.typesafe.akka" %% "akka-http-xml" % "10.0.3",
    "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
    "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "test",
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
    "org.mockito" % "mockito-all" % "1.10.19" % "test"
    
  ),
  pomExtra := 
    <scm>
      <url>scm:git:https://github.com/EmilDafinov/scala-ad-sdk</url>
      <connection>scm:git:https://github.com/EmilDafinov/scala-ad-sdk</connection>
    </scm>
    <developers>
      <developer>
        <id>edafinov</id>
        <name>Emil Dafinov</name>
        <url>http://www.example.com/~emil</url>
      </developer>
    </developers>,
  publishArtifact in Test := false,
  publishMavenStyle := false,
  publishTo := {
    if (isSnapshot.value)
      Some("Artifactory Realm" at "https://oss.jfrog.org/artifactory/oss-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
    else
      publishTo.value
  },
  credentials := {
    if(isSnapshot.value) 
      List(Path.userHome / ".bintray" / ".artifactory").filter(_.exists).map(Credentials(_))
    else
      credentials.value
  },
  bintrayReleaseOnPublish := false
)
