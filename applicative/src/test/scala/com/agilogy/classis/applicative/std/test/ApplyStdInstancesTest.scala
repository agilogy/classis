package com.agilogy.classis.applicative.std.test

import com.agilogy.classis.apply.Apply
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

class ApplyStdInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("should exist an instance for Option"){
    assert(Apply[Option].apply[Int,String](Some(_.toString))(Some(3)) === Some("3"))
    assert(Apply[Option].apply[Int,String](Some(_.toString))(None) === None)
    assert(Apply[Option].apply[Int,String](None)(Some(3)) === None)
  }

  it("should exist an instance for List"){
    assert(Apply[List].apply[Int,String](List(_.toString, _.toString.reverse))(List(34,97)) === List("34","97","43","79"))
    assert(Apply[List].apply[Int,String](List(_.toString))(List.empty) === List.empty)
    assert(Apply[List].apply[Int,String](List.empty)(List(3,4)) === List.empty)
  }

  it("should exist an instance for Seq"){
    assert(Apply[Seq].apply[Int,String](Seq(_.toString, _.toString.reverse))(Seq(34,97)) === Seq("34","97","43","79"))
    assert(Apply[Seq].apply[Int,String](Seq(_.toString))(Seq.empty) === Seq.empty)
    assert(Apply[Seq].apply[Int,String](Seq.empty)(Seq(3,4)) === Seq.empty)
  }

}
