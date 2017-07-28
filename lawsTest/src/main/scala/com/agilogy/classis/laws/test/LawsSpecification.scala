package com.agilogy.classis.laws.test

import com.agilogy.classis.laws.{Law, Law1, Law2, Law3}
import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.Prop.forAll

import scala.reflect.runtime.universe.TypeTag

class LawsSpecification(name:String) extends Properties(name){

  private val lst = implicitly[TypeTag[LawsSpecification]]

  def checkLaws[T: Arbitrary: TypeTag](laws:Seq[Law[T]]): Unit = {
    val tt = implicitly[TypeTag[T]].tpe.asSeenFrom(lst.tpe,lst.tpe.typeSymbol).dealias
    laws.foreach {
      case Law1(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
      case Law2(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
      case Law3(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
    }
  }

}
