import sbt._
import sbt.Keys._

object Classis extends BaseBuild {

  lazy val classis = project.in(file("."))
    .settings(moduleName := "root")
    .settings(commonSettings)
    .settings(noPublishSettings)
    .aggregate(classisLaws, classisEqual, classisOrder, classisFunctor,classisMonoid,classisApplicative)

  lazy val classisLaws = project.in(file("laws"))
    .settings(moduleName := "classis-laws")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2")

  lazy val classisLawsTest = project.in(file("lawsTest"))
    .settings(moduleName := "classis-laws-test")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.13.4"
    ))
    .dependsOn(classisLaws)
    .settings(version := "0.2")

  lazy val classisEqual = project.in(file("equal"))
    .settings(moduleName := "classis-equal")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .dependsOn(classisLaws, classisLawsTest % "test")
    .settings(version := "0.2")

  lazy val classisOrder = project.in(file("order"))
    .settings(moduleName := "classis-order")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .dependsOn(classisLaws, classisLawsTest % "test", classisEqual)
    .settings(version := "0.2")

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
