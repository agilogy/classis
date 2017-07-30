import sbt._
import sbt.Keys._

object Classis extends BaseBuild {

  lazy val classis = project.in(file("."))
    .settings(moduleName := "root")
    .settings(commonSettings)
    .settings(noPublishSettings)
    .aggregate(laws, equal, order, functor, monoid, applicative)

  lazy val laws = project.in(file("laws"))
    .settings(moduleName := "laws")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2")

  lazy val lawsTest = project.in(file("lawsTest"))
    .settings(moduleName := "laws-test")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.13.4"
    ))
    .dependsOn(laws)
    .settings(version := "0.2")

  lazy val equal = project.in(file("equal"))
    .settings(moduleName := "equal")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .dependsOn(laws, lawsTest % "test")
    .settings(version := "0.2")

  lazy val order = project.in(file("order"))
    .settings(moduleName := "order")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .dependsOn(laws, lawsTest % "test", equal)
    .settings(version := "0.2")

  lazy val monoid = project.in(file("monoid"))
    .settings(moduleName := "monoid")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    ))
    .dependsOn(equal, laws, lawsTest % "test")
    .settings(version := "0.2")

  lazy val functor = project.in(file("functor"))
    .settings(moduleName := "functor")
    .settings(commonSettings)
    .settings(testSettings)
    .dependsOn(equal, laws, lawsTest % "test")
    .settings(version := "0.2")

  lazy val applicative = project.in(file("applicative"))
    .settings(moduleName := "applicative")
    .settings(commonSettings)
    .settings(testSettings)
    .settings(version := "0.2")
    .dependsOn(functor)

}
