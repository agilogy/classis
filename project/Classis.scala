import sbt._
import sbt.Keys._

object Classis extends BaseBuild {

  lazy val classis = project.in(file("."))
    .settings(moduleName := "root")
    .settings(commonSettings)
    .settings(noPublishSettings)
    .aggregate(classisMonoid,classisApplicative)

  lazy val classisMonoid = project.in(file("monoid"))
    .settings(moduleName := "classis-monoid")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2.rc1")

  lazy val classisApplicative = project.in(file("applicative"))
    .settings(moduleName := "classis-applicative")
    .settings(commonSettings)

}
