name := "elastic4s-client-aws"

organization := "com.github.pjfanning"

ThisBuild / scalaVersion := "2.13.6"

ThisBuild / crossScalaVersions := Seq("2.12.15", "2.13.6")

ThisBuild / scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked")

val awsSdkVersion = "2.17.46"
val elastic4sVersion = "7.14.1"

libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion,
  "com.sksamuel.exts" %% "exts" % "1.61.1",
  "org.slf4j" % "slf4j-api" % "1.7.32",
  "software.amazon.awssdk" % "auth" % awsSdkVersion,
  "software.amazon.awssdk" % "core" % awsSdkVersion,
  "software.amazon.awssdk" % "regions" % awsSdkVersion,
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

publishMavenStyle := true

Test / publishArtifact := false

pomIncludeRepository := { x => false }

homepage := Some(new URL("https://github.com/pjfanning/elastic4s-client-aws"))

startYear := Some(2021)

licenses := Seq(("Apache License 2.0", new URL("http://www.apache.org/licenses/LICENSE-2.0.html")))

releasePublishArtifactsAction := PgpKeys.publishSigned.value

pomExtra := (
  <issueManagement>
    <system>github</system>
    <url>https://github.com/pjfanning/elastic4s-client-aws/issues</url>
  </issueManagement>
  <developers>
    <developer>
      <id>sksamuel</id>
      <name>Sam Samuel</name>
      <url>https://github.com/sksamuel</url>
    </developer>
  </developers>
)

ThisBuild / githubWorkflowJavaVersions := Seq("adopt@1.8")
ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches:= Seq()
