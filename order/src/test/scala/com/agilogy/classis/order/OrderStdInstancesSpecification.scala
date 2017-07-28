package com.agilogy.classis.order

import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.order.std.OrderStdInstances._
import org.scalacheck.{Arbitrary, Gen}

class OrderStdInstancesSpecification extends LawsSpecification("order") {

  checkLaws(Order.laws[Boolean])
  checkLaws(Order.laws[Byte])
  checkLaws(Order.laws[Short])
  checkLaws(Order.laws[Int])
  checkLaws(Order.laws[Long])
  checkLaws(Order.laws[Float])
  checkLaws(Order.laws[Double])

  implicit val orderingArbitrary: Arbitrary[Ordering] = Arbitrary(Gen.oneOf(Lt, Eq, Gt))
  checkLaws(Order.laws[Ordering])

  checkLaws(Order.laws[Option[Double]])
  checkLaws(Order.laws[Either[Int, Double]])

  checkLaws(Order.laws[(Int, Double)])

}
