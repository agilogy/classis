import bintray.BintrayKeys._
import sbt._
import sbt.Keys._
import scoverage.ScoverageSbtPlugin.ScoverageKeys

trait BaseBuild extends Build{

  lazy val testSettings = Seq(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
  )

  lazy val buildSettings = Seq(
    organization := "com.agilogy",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.6", "2.11.7"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.8.0"
  )

  lazy val commonScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    //    "-language:existentials",
    //    "-language:higherKinds",
    //    "-language:implicitConversions",
    //    "-language:experimental.macros",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture",
    "-Xfatal-warnings",
    "-Ywarn-dead-code",
    "-Yinline-warnings" //??
    //    "-P:linter:disable:PreferIfToBooleanMatch"
  )

  lazy val warnUnusedImport = Seq(
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 10)) =>
          Seq()
        case Some((2, n)) if n >= 11 =>
          Seq("-Ywarn-unused-import")
      }
    },
    scalacOptions in (Compile, console) ~= {_.filterNot("-Ywarn-unused-import" == _)},
    scalacOptions in (Test, console) <<= (scalacOptions in (Compile, console))
  )

  lazy val baseSettings = Seq(
    scalacOptions ++= commonScalacOptions,
    libraryDependencies ++= Seq(
    ),
    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oDF")
  ) ++ warnUnusedImport

  lazy val scoverageSettings = Seq(
    ScoverageKeys.coverageMinimum := 60,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := scalaBinaryVersion.value != "2.10"
    //    ScoverageKeys.coverageExcludedPackages := "cats\\.bench\\..*"
  )

  lazy val publishSettings = Seq(
    bintrayRepository := "scala",
    bintrayOrganization := Some("agilogy"),
    bintrayPackageLabels := Seq("scala"),
    licenses +=("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    publishMavenStyle := isSnapshot.value,
    publishTo := {
      val nexus = "http://188.166.95.201:8081/content/repositories/snapshots"
      if (isSnapshot.value) Some("snapshots"  at nexus)
      else publishTo.value
    },
    bintrayReleaseOnPublish := !isSnapshot.value
  )

  lazy val noPublishSettings = Seq(
    publish := (),
    publishLocal := (),
    publishArtifact := false
  )

  lazy val commonSettings = buildSettings ++ baseSettings ++ scoverageSettings ++ publishSettings

}