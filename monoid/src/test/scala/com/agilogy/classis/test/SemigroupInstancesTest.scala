package com.agilogy.classis.test

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.FunSpec

import scala.collection.immutable.{HashMap, HashSet}

import com.agilogy.classis.monoid.Semigroup.syntax._
import com.agilogy.classis.monoid.std.SemigroupStdInstances._

class SemigroupInstancesTest extends FunSpec with TypeCheckedTripleEquals{

  it("should exist an instance for Option if the content is a Semigroup"){
    assert(Option.empty[Int].append(Option.empty[Int]) === None)
    assert(Option.empty[Int].append(Option(3)) === Option(3))
    assert(Option(5).append(Option.empty[Int]) === Option(5))
    assert(Option(3).append(Option(5)) === Option(8))
  }

  it("should exist an instance for List"){
    assert(List(1, 2, 3).append(List(4,5)) === List(1,2,3,4,5))
  }

  it("should exist an instance for Stream"){
    assert(Stream(1, 2, 3).append(Stream(4,5)) === Stream(1,2,3,4,5))
  }

  it("should exist an instance for Vector"){
    assert(Vector(1, 2, 3).append(Vector(4,5)) === Vector(1,2,3,4,5))
  }

  it("shold exist an instance for Set"){
    assert(Set(1, 2, 3).append(Set(4,5)) === Set(1,2,3,4,5))
  }

  it("shold exist an instance for HashSet"){
    assert(HashSet(1, 2, 3).append(HashSet(4,5)) === HashSet(1,2,3,4,5))
  }

  it("should exist an instance for Map"){
    assert(Map("a" -> 1, "b" -> 2, "c" -> 3).append(Map("d" -> 4, "e" -> 5)) === Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4, "e" -> 5))
  }

  it("should exist an instance for HashMap"){
    assert(HashMap("a" -> 1, "b" -> 2, "c" -> 3).append(HashMap("d" -> 4, "e" -> 5)) === HashMap("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4, "e" -> 5))
  }

}
