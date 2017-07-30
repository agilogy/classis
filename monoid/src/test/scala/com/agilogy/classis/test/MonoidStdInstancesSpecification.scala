package com.agilogy.classis.test

import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.monoid.std.MonoidStdInstances._
import com.agilogy.classis.equal.std.EqualStdInstances._

class MonoidStdInstancesSpecification extends LawsSpecification("monoid"){

  check(MonoidLawsProperties[Byte])
  check(MonoidLawsProperties[Short])
  check(MonoidLawsProperties[Int])
  check(MonoidLawsProperties[Long])

//  check(MonoidLawsProperties[Float])
  check(MonoidLawsProperties[Double])

  check(MonoidLawsProperties[List[String]])
  check(MonoidLawsProperties[Seq[String]])
  check(MonoidLawsProperties[Set[String]])

  check(MonoidLawsProperties[(Int,Seq[String])])

}
