package com.agilogy.classis.order.test

import com.agilogy.classis.laws.test.LawsProperties
import com.agilogy.classis.order.Order
import org.scalacheck.Arbitrary
import scala.reflect.runtime.universe.TypeTag

class OrderLawsProperties[T: Order: Arbitrary: TypeTag] extends LawsProperties{

  override def properties: LawChecks = {
    val laws = Order.laws[T]
    Seq(
      laws.reflexiveEqual,
      laws.commutativeEqual,
      laws.transitiveEqual,
      laws.transitiveLteq,
      laws.totalLteq,
      laws.antisymmetryLteqEqual
    )
  }

  override def typeName: String = implicitly[TypeTag[T]].tpe.dealias.toString
}

object OrderLawsProperties{
  def apply[T: Order: Arbitrary: TypeTag]:OrderLawsProperties[T] = new OrderLawsProperties[T]

}
