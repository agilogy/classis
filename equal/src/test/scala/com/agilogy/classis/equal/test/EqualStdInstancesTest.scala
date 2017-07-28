package com.agilogy.classis.equal.test

import com.agilogy.classis.equal.Equal
import org.scalatest.{FunSpec, NonImplicitAssertions}
import Equal.syntax._
import com.agilogy.classis.equal.std.EqualStdInstances._

case class Coords(x:Int, y:Int)

class EqualStdInstancesTest extends FunSpec with NonImplicitAssertions{

  describe("syntax") {

    it("should support primitive numbers") {
      assert(3 === 3)
      assertTypeError("3 === 3l")
    }

    it("should support tuples") {
      assert((3, "hello") === ((3, "hello")))
    }

    it("should support case classes") {
      assert(Coords(23, 12) === Coords(23, 12))
      assert(Coords(23, 12) !== Coords(1, 2))
    }

  }

}
