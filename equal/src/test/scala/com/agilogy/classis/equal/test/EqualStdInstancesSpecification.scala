package com.agilogy.classis.equal.test

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.test.LawsSpecification
import com.agilogy.classis.equal.std.EqualStdInstances._

class EqualStdInstancesSpecification extends LawsSpecification("equal") {

  checkLaws(Equal.laws[Boolean])
  checkLaws(Equal.laws[Byte])
  checkLaws(Equal.laws[Short])
  checkLaws(Equal.laws[Int])
  checkLaws(Equal.laws[Long])
  checkLaws(Equal.laws[Float])
  checkLaws(Equal.laws[Double])
  checkLaws(Equal.laws[Char])
  checkLaws(Equal.laws[String])

  checkLaws(Equal.laws[Option[Int]])
  checkLaws(Equal.laws[Either[String,Int]])

  checkLaws(Equal.laws[(Long,Double)])

  checkLaws(Equal.laws[List[Int]])
  checkLaws(Equal.laws[Seq[Int]])
  checkLaws(Equal.laws[Set[Int]])
  checkLaws(Equal.laws[Map[Int,Char]])
//  checkLaws(Equal.laws[Queue[Int]])
}
