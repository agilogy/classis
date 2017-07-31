package com.agilogy.classis.functor.test

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.functor.std.FunctorStdInstances._
import com.agilogy.classis.equal.std.EqualStdInstances._
import Equal.syntax._

class FunctorStdInstancesSpecification extends LawsSpecification("functor"){

  check(FunctorLawsProperties[Option, Int, String, String])
  check(FunctorLawsProperties[Either[Int,?], Int, String, String])

  check(FunctorLawsProperties[List, Int, String, String])
  check(FunctorLawsProperties[Seq, Int, String, String])
//  check(FunctorLawsProperties[Stream, Int, String, String])
//  check(FunctorLawsProperties[Function0, Int, String, String])

  // Trick: Use Boolean => ? to test Function1, sinze we can write an Equal instance for Boolean => ?
  implicit def f1eq[T: Equal] = new Equal[Boolean => T] {
    override def equal(x: Boolean => T, y: Boolean => T): Boolean = {
      (x(true) === y(true)) && (x(false) === y(false))
    }
  }

  check(FunctorLawsProperties[Boolean => ?, Int, String, String])

}
