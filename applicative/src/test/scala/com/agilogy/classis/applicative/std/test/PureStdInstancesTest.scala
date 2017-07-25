package com.agilogy.classis.applicative.std.test

import com.agilogy.classis.applicative.Pure
import com.agilogy.classis.applicative.std.PureStdInstances._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

class PureStdInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("should exist an instance for Option"){
    assert(Pure[Option].pure(5) === Option(5))
  }

  it("should exist an instance for List"){
    assert(Pure[List].pure(1) === List(1))
  }

  it("should exist an instance for Stream"){
    assert(Pure[Stream].pure(1) === Stream(1))
  }


}
