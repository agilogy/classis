package com.agilogy.classis.applicative.std.test

import com.agilogy.classis.applicative.test.ApplyLawsProperties
import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.applicative.std.ApplyStdInstances._
import com.agilogy.classis.equal.std.EqualStdInstances._

class ApplyStdInstancesSpecification extends LawsSpecification("apply"){

  check(ApplyLawsProperties[Option,Int,Int,Int])
  check(ApplyLawsProperties[List,Int,Int,Int])
  check(ApplyLawsProperties[Seq,Int,Int,Int])

}