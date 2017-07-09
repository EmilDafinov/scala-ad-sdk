val AKKA_VERSION = "2.5.3"
val AKKA_HTTP_VERSION = "10.0.9"
val SCALATEST_VERSION = "3.0.3"
val SIGNPOST_VERSION = "1.2.1.2"
val JSON4S_VERSION = "3.5.2"
val PROJECT_HOMEPAGE_URL = "https://github.com/EmilDafinov/scala-ad-sdk"
val BINTRAY_USER = System.getenv("BINTRAY_USER")
val BINTRAY_PASSWORD = System.getenv("BINTRAY_PASS")

lazy val versionSettings = Seq(
  //  The 'version' setting is not set on purpose: its value is generated automatically by the sbt-dynver plugin
  //  based on the git tag/sha. Here we're just tacking on the maven-compatible snapshot suffix if needed
  version := {
    val snapshotVersion = dynverGitDescribeOutput.value
      .filter(gitVersion => gitVersion.isSnapshot())
      .map(output => output.version + "-SNAPSHOT")

    snapshotVersion.getOrElse(version.value)
  }
)

lazy val publicationSettings = Seq(
  publishTo := {
    if (isSnapshot.value)
      Some(s"Artifactory Realm" at "https://oss.jfrog.org/artifactory/oss-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
    else
      publishTo.value //Here we are assuming that the bintray-sbt plugin does its magic and the publish settings are set to
    //point to Bintray
  },
  credentials := {
    if (isSnapshot.value) {
      Seq(
        Credentials(
          realm = "Artifactory Realm",
          host = "oss.jfrog.org",
          userName = BINTRAY_USER,
          passwd = BINTRAY_PASSWORD
        )
      )
    }
    else
      credentials.value
  },
  publishArtifact in Test := false,
  bintrayReleaseOnPublish := !isSnapshot.value
)


lazy val projectMetadataSettings = Seq(
  licenses += "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"),
  homepage := Some(url(PROJECT_HOMEPAGE_URL)),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url(PROJECT_HOMEPAGE_URL),
      connection = "scm:git:git@github.com:EmilDafinov/scala-ad-sdk.git"
    )
  ),
  developers := List(
    Developer(
      id = "edafinov",
      name = "Emil Dafinov",
      email = "emiliorodo@gmail.com",
      url = url("https://github.com/EmilDafinov")
    )
  )
)

lazy val scalaAdSdk = (project in file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(projectMetadataSettings)
  .settings(versionSettings)
  .settings(publicationSettings)
  .settings(
    scalaVersion := "2.12.2",

    organization := "com.github.emildafinov",
    name := "scala-ad-sdk",

    coverageExcludedFiles := ".*Module.*;",
    libraryDependencies ++= Seq(
      //Application config
      "com.typesafe" % "config" % "1.3.1",

      //Logging
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1",

      //Authentication
      "oauth.signpost" % "signpost-core" % SIGNPOST_VERSION,
      "oauth.signpost" % "signpost-commonshttp4" % SIGNPOST_VERSION,

      //Http
      "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION,
      "com.typesafe.akka" %% "akka-http-core" % AKKA_HTTP_VERSION,
      "com.typesafe.akka" %% "akka-stream" % AKKA_VERSION, // Added to allow using the latest version of Akka with Akka Http 10.0.7
      "com.typesafe.akka" %% "akka-http" % AKKA_HTTP_VERSION,
      "com.typesafe.akka" %% "akka-http-testkit" % AKKA_HTTP_VERSION,
      "com.typesafe.akka" %% "akka-http-xml" % AKKA_HTTP_VERSION,
      "io.github.lhotari" %% "akka-http-health" % "1.0.8",

      //Json
      "org.json4s" %% "json4s-jackson" % JSON4S_VERSION,
      "org.json4s" %% "json4s-ext" % JSON4S_VERSION,
      "de.heikoseeberger" %% "akka-http-json4s" % "1.17.0",

      //Test
      "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
      "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "it,test",
      "org.mockito" % "mockito-all" % "1.10.19" % "it,test",
      "com.github.tomakehurst" % "wiremock" % "2.6.0" % "it,test"
    )
  )
