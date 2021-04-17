name := "elastic4s-client-aws"

organization := "com.github.pjfanning"

scalaVersion := "2.13.5"

ThisBuild / scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")

val awsSdkVersion = "2.16.43"
val elastic4sVersion = "7.3.6"

libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
  "com.sksamuel.exts" %% "exts" % "1.61.1",
  "org.slf4j" % "slf4j-api" % "1.7.30",
  "software.amazon.awssdk" % "auth" % awsSdkVersion,
  "software.amazon.awssdk" % "core" % awsSdkVersion,
  "software.amazon.awssdk" % "regions" % awsSdkVersion,
  "software.amazon.awssdk" % "regions" % awsSdkVersion,
  "org.scalatest" %% "scalatest" % "3.2.7" % Test
)

