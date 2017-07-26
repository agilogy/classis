package com.agilogy.classis.test

import com.agilogy.classis.monoid.Zero
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

import scala.collection.immutable.{HashMap, HashSet}
import com.agilogy.classis.monoid.std.ZeroStdInstances._

class ZeroStdInstancesTest extends FunSpec with TypeCheckedTripleEquals{


  it("should exist an instance for Option with whatever type inside"){
    assert(Zero[Option[java.sql.Connection]].zero === None)
  }

  // C[X] <: TraversableLike[X, C[X]]

  it("should exist an instance for List"){
    assert(Zero[List[Int]].zero === List.empty)
  }

  it("should exist an instance for Stream"){
    assert(Zero[Stream[Int]].zero === Stream.empty)
  }

  it("should exist an instance for Vector"){
    assert(Zero[Vector[Int]].zero === Vector.empty)
  }

  it("shold exist an instance for HashSet"){
    assert(Zero[HashSet[Int]].zero === HashSet.empty[Int])
  }

  // M[X,Y] <: Map[X, Y]

  it("should exist an instance for HashMap"){
    assert(Zero[HashMap[String,Int]].zero === HashMap.empty[String,Int])
  }

}
