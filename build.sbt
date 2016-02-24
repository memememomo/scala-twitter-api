import AssemblyKeys._

name := "twitter-api"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "com.typesafe" % "config" % "1.3.0"
)

assemblySettings
