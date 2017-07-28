package com.agilogy.classis.test

import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.monoid.Monoid
import com.agilogy.classis.monoid.std.MonoidStdInstances._
import com.agilogy.classis.equal.std.EqualStdInstances._

class MonoidStdInstancesSpecification extends LawsSpecification("monoid"){

  checkLaws(Monoid.laws[Byte])
  checkLaws(Monoid.laws[Short])
  checkLaws(Monoid.laws[Int])
  checkLaws(Monoid.laws[Long])

//  checkLaws(Monoid.laws[Float])
  checkLaws(Monoid.laws[Double])

  checkLaws(Monoid.laws[List[String]])
  checkLaws(Monoid.laws[Seq[String]])
  checkLaws(Monoid.laws[Set[String]])

  checkLaws(Monoid.laws[(Int,Seq[String])])

}
