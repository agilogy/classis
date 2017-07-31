package com.agilogy.classis.test

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsProperties
import com.agilogy.classis.monoid.Monoid
import org.scalacheck.Arbitrary

import scala.reflect.runtime.universe.TypeTag


class MonoidLawsProperties[T: Monoid: Arbitrary: TypeTag: Equal] extends LawsProperties{

  override def typeName: String = implicitly[TypeTag[T]].tpe.toString

  override def properties: LawChecks = {
    val laws = Monoid.laws[T]
    Seq(
      laws.associativeAppend,
      laws.leftIdentityAppendZero,
      laws.rightIdentityAppendZero
    )
  }
}

object MonoidLawsProperties{
  def apply[T: Monoid: Arbitrary: TypeTag: Equal]:MonoidLawsProperties[T] = new MonoidLawsProperties[T]
}
