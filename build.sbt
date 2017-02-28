val AKKA_VERSION = "2.4.16"
val AKKA_HTTP_VERSION = "10.0.4"
val SCALATEST_VERSION = "3.0.0"

lazy val scalaAdSdk = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, UniversalPlugin)
  .settings(baseSettings:_*)
lazy val baseSettings = Seq(
  scalaVersion := "2.12.1",

  organization := "com.github.emildafinov",
  name := "scala-ad-sdk",
  version := "1.8",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),

  resolvers += Resolver.bintrayRepo("lhotari","releases"),
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
    "com.typesafe.akka" %% "akka-http-spray-json" % AKKA_HTTP_VERSION,
    "com.typesafe.akka" %% "akka-http-xml" % AKKA_HTTP_VERSION,
    "io.github.lhotari" %% "akka-http-health" % "1.0.3",
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
  bintrayReleaseOnPublish := !isSnapshot.value
)
