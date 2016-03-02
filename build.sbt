name := "twitter-api"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "org.twitter4j" % "twitter4j-stream" % "4.0.2",
  "com.typesafe" % "config" % "1.3.0",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7"
)

resolvers += Resolver.sonatypeRepo("public")
