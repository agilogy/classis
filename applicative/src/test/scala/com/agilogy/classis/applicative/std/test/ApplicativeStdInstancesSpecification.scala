package com.agilogy.classis.applicative.std.test

import com.agilogy.classis.applicative.test.ApplicativeLawsProperties
import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.applicative.std.ApplicativeStdInstances._
import com.agilogy.classis.equal.std.EqualStdInstances._

class ApplicativeStdInstancesSpecification extends LawsSpecification("applicative"){

  check(ApplicativeLawsProperties[Option,Int,Int,Int])
  check(ApplicativeLawsProperties[Either[Int,?],Int,Int,Int])
  check(ApplicativeLawsProperties[List,Int,Int,Int])
  check(ApplicativeLawsProperties[Seq,Int,Int,Int])

}
