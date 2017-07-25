package com.agilogy.classis.functor.std.test

import com.agilogy.classis.functor.Functor
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

import com.agilogy.classis.functor.std.FunctorStdInstances._

class FunctorStdInstancesTest extends FunSpec with TypeCheckedTripleEquals {

  it("should exist a functor for Function1") {
    val f2: (Int) => Int = _ * 3
    val f3: (Int,Int) => Int = _ * _
    import Functor.toFunctorAbOps
    assert(f2.map(_ + 1)(4) === 4*3+1)
    assert(f3.tupled.map(_ + 1)((4,5)) === 4*5+1)
  }

}
