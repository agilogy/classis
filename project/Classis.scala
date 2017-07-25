import sbt._
import sbt.Keys._

object Classis extends BaseBuild {

  lazy val classis = project.in(file("."))
    .settings(moduleName := "root")
    .settings(commonSettings)
    .settings(noPublishSettings)
    .aggregate(classisFunctor,classisMonoid,classisApplicative)

  lazy val classisMonoid = project.in(file("monoid"))
    .settings(moduleName := "classis-monoid")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .settings(version := "0.2")

  lazy val classisFunctor = project.in(file("functor"))
    .settings(moduleName := "classis-functor")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2")

  lazy val classisApplicative = project.in(file("applicative"))
    .settings(moduleName := "classis-applicative")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2")
    .dependsOn(classisFunctor)

}
