package com.agilogy.classis.bind

import org.scalatest.{FunSpec, NonImplicitAssertions}

import Bind.syntax._
import com.agilogy.classis.equal.Equal.syntax._

class BindTest extends FunSpec with NonImplicitAssertions {

  it("should work"){
    Bind[List].flatMap(List(1,2,3))(i => List(i,i+1))
  }

  it("should have a product function"){
    assert(List(1, 2, 3).applyProduct(List(4,5)) === List((1,4),(1,5),(2,4),(2,5),(3,4),(3,5)))
  }

  it("shoud have a fmap function"){
    assert(List(1,2,3).fmap(_ + 1) === List(2,3,4))
  }

}
