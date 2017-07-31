package com.agilogy.classis.equal.test

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import scala.reflect.runtime.universe.TypeTag
import org.scalacheck.Arbitrary

class EqualLawsProperties[T: Equal: Arbitrary: TypeTag] extends LawsProperties{

  override def properties: LawChecks = {
    val laws = Equal.laws[T]
    Seq(
      laws.reflexiveEqual,
      laws.commutativeEqual,
      laws.transitiveEqual
    )
  }

  override def typeName: String = implicitly[TypeTag[T]].tpe.toString
}

object EqualLawsProperties{
  def apply[T: Equal : Arbitrary: TypeTag]: EqualLawsProperties[T] = new EqualLawsProperties[T]
}
