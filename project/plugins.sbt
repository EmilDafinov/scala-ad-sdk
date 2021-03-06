logLevel := Level.Warn

resolvers ++= Seq(
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com",
  "Jenkins repo" at "http://repo.jenkins-ci.org/public/"
)
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.5")

addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.4.0")

addSbtPlugin("com.softwaremill.clippy" % "plugin-sbt" % "0.5.3")

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "2.0.0")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

