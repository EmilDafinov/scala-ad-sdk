logLevel := Level.Warn

resolvers ++= Seq(
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com",
  "Jenkins repo" at "http://repo.jenkins-ci.org/public/"
)
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC6")

addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.4.0")

addSbtPlugin("com.softwaremill.clippy" % "plugin-sbt" % "0.5.3")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "2.0.0")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

