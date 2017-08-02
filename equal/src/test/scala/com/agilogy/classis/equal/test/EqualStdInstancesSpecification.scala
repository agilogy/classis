package com.agilogy.classis.equal.test

import com.agilogy.classis.laws.test.LawsSpecification

class EqualStdInstancesSpecification extends LawsSpecification("equal") {

  check(EqualLawsProperties[Boolean])

  check(EqualLawsProperties[Byte])
  check(EqualLawsProperties[Short])
  check(EqualLawsProperties[Int])
  check(EqualLawsProperties[Long])
  check(EqualLawsProperties[Float])
  check(EqualLawsProperties[Double])
  check(EqualLawsProperties[Char])
  check(EqualLawsProperties[String])

  check(EqualLawsProperties[Option[Int]])
  check(EqualLawsProperties[Either[String,Int]])

  check(EqualLawsProperties[(Long,Double)])

  check(EqualLawsProperties[List[Int]])
  check(EqualLawsProperties[Seq[Int]])
  check(EqualLawsProperties[Set[Int]])
  check(EqualLawsProperties[Map[Int,Char]])
////  check(EqualLawsProperties[Queue[Int]])
}
