logLevel := Level.Warn

resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.5")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.4")

addSbtPlugin("com.softwaremill.clippy" % "plugin-sbt" % "0.5.1")

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")
