package com.agilogy.classis.test

import com.agilogy.classis.monoid.{Semigroup, Zero}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec
//import com.agilogy.classis.monoid.std.MonoidStdInstances._
import com.agilogy.classis.monoid.std.SemigroupStdInstances._
import com.agilogy.classis.monoid.std.ZeroStdInstances._
import com.agilogy.classis.monoid.Semigroup.ops._

case class Coords(x:Int, y:Int)

class MonoidDerivedInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("should derive a monoid instance for a tuple"){
    val x: (Long, Int) = (1l,1)
    val y: (Long, Int) = (2l,23)
    assert(x.append(y) === ((3l,24)))
    assert(Semigroup.apply[(Long,Int)].append(x,y) === ((3l,24)))
    assert(Zero.apply[(Long,String)].zero === ((0l,"")))
//    assert(Monoid[(Long,Int)].append(x,y) === ((3l,24)))
//    assert(Monoid[(Long,String)].zero === ((0l,"")))
  }

  it("should derive a monoid for a case class"){
//    assert(Coords(23,34).append(Coords(45,56)) === Coords(23+45,34+56))
  }

}
