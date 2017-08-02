package com.agilogy.classis.test

import com.agilogy.classis.monoid.Monoid
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec
import com.agilogy.classis.monoid.Monoid.syntax._
import com.agilogy.classis.semigroup.Semigroup
import com.agilogy.classis.zero.Zero
import shapeless.{::, HNil}

case class Coords(x:Int, y:Int)

class MonoidDerivedInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("fooo"){
    assert(List(1,2).append(List.empty) === List(1,2))
  }

  it("should derive a Zero instance for HLists") {
    assert(Zero[Int :: HNil].zero === 0 :: HNil)
  }

  it("should derive a Zero instance for case classes"){
    assert(Zero[Coords].zero === Coords(0,0))
  }

  it("should derive a monoid instance for a tuple"){
    val x: (Long, Int) = (1l,1)
    val y: (Long, Int) = (2l,23)
    assert(x.append(y) === ((3l,24)))
    assert(Semigroup.apply[(Long,Int)].append(x,y) === ((3l,24)))
    assert(Zero.apply[(Long,String)].zero === ((0l,"")))
    assert(Monoid[(Long,Int)].append(x,y) === ((3l,24)))
    assert(Monoid[(Long,String)].zero === ((0l,"")))
  }

  it("should be able to use the monoid syntax"){
    def sum[T:Monoid](x:T,y:T) = x.append(y)
    assert(sum(1,2) === 3)
  }

  it("should derive a monoid for a case class"){
    assert(Coords(23,34).append(Coords(45,56)) === Coords(23+45,34+56))
    assert(Monoid[Coords].zero === Coords(0,0))
  }

}
