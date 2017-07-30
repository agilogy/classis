package com.agilogy.classis.order

import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.order.std.OrderStdInstances._
import com.agilogy.classis.order.test.OrderLawsProperties
import org.scalacheck.{Arbitrary, Gen}

class OrderStdInstancesSpecification extends LawsSpecification("order") {

  check(OrderLawsProperties[Boolean])
  check(OrderLawsProperties[Boolean])
  check(OrderLawsProperties[Byte])
  check(OrderLawsProperties[Short])
  check(OrderLawsProperties[Int])
  check(OrderLawsProperties[Long])
  check(OrderLawsProperties[Float])
  check(OrderLawsProperties[Double])

  implicit val orderingArbitrary: Arbitrary[Ordering] = Arbitrary(Gen.oneOf(Lt, Eq, Gt))
  check(OrderLawsProperties[Ordering])

  check(OrderLawsProperties[Option[Double]])
  check(OrderLawsProperties[Either[Int, Double]])

  check(OrderLawsProperties[(Int, Double)])

}
