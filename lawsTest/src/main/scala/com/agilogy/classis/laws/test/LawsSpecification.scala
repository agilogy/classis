package com.agilogy.classis.laws.test

import com.agilogy.classis.laws.{Law, Law1, Law2, Law3}
import org.scalacheck.{Arbitrary, Properties}
import org.scalacheck.Prop.forAll

import scala.reflect.ClassTag

class LawsSpecification(name:String) extends Properties(name){

  def checkLaws[T: Arbitrary: ClassTag](laws:Seq[Law[T]]): Unit = {
    val tt = implicitly[ClassTag[T]]
    laws.foreach {
      case Law1(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
      case Law2(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
      case Law3(n, fns, c) => property(s"$n${fns.mkString("(",",",")")}($tt)") = forAll(c)
    }
  }

}
