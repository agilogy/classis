package com.agilogy.classis.applicative.std.test

import com.agilogy.classis.applicative.Applicative
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

import com.agilogy.classis.applicative.std.ApplicativeStdInstances._

class ApplicativeStdInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("should exist an instance for Option"){
    assert(Applicative[Option].apply[Int,String](Some(_.toString))(Some(3)) === Some("3"))
    assert(Applicative[Option].pure(34) === Some(34))
  }

  it("should exist an instance for List"){
    assert(Applicative[List].apply[Int,String](List(_.toString, _.toString.reverse))(List(34,97)) === List("34","97","43","79"))
    assert(Applicative[List].pure(34) === List(34))
  }

  it("should exist an instance for Seq"){
    assert(Applicative[Seq].apply[Int,String](Seq(_.toString, _.toString.reverse))(Seq(34,97)) === Seq("34","97","43","79"))
    assert(Applicative[Seq].pure(34) === Seq(34))
  }

}

