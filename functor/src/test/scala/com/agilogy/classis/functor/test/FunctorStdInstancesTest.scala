package com.agilogy.classis.functor.test

import com.agilogy.classis.functor.Functor.syntax._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

class FunctorStdInstancesTest extends FunSpec with TypeCheckedTripleEquals {

  it("should exist a functor for Function1") {
    val f2: (Int) => Int = _ * 3
    val f3: (Int,Int) => Int = _ * _
    assert(f2.map(_ + 1)(4) === 4*3+1)
    assert(f3.tupled.map(_ + 1)((4,5)) === 4*5+1)
  }

  it("should lift any function to a functor"){
    val f: Int => Int = _ + 3
    assert(f.lift[List].apply(List(3,4)) === List(6,7))
  }

}
