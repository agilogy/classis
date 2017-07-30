package com.agilogy.classis.laws.test

import com.agilogy.classis.laws.{Law, Law1, Law2, Law3}
import org.scalacheck.{Arbitrary, Prop}
import org.scalacheck.Prop.forAll

import scala.language.implicitConversions

trait LawsProperties {

  def typeName:String

  protected type LawCheck = (String,Prop)
  protected type LawChecks = Seq[LawCheck]

  def name(l:Law):String = s"${l.name}(${l.functionNames.mkString(", ")})"

  implicit protected def law2Property[A: Arbitrary](l:Law1[A]): (String, Prop) = name(l) -> forAll(l.check)
  implicit protected def law2Property[A: Arbitrary, B: Arbitrary](l:Law2[A,B]): (String, Prop) = name(l) -> forAll(l.check)
  implicit protected def law2Property[A: Arbitrary, B: Arbitrary, C: Arbitrary](l:Law3[A,B,C]): (String, Prop) = name(l) -> forAll(l.check)

  def properties: LawChecks
}
