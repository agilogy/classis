package com.agilogy.classis.test

import com.agilogy.classis.laws.test.LawsSpecification

class MonoidStdInstancesSpecification extends LawsSpecification("monoid"){

  check(MonoidLawsProperties[Byte])
  check(MonoidLawsProperties[Short])
  check(MonoidLawsProperties[Int])
  check(MonoidLawsProperties[Long])

  check(MonoidLawsProperties[List[String]])
  check(MonoidLawsProperties[Seq[String]])
  check(MonoidLawsProperties[Set[String]])

  check(MonoidLawsProperties[(Int,Seq[String])])

}
