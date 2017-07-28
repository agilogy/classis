package com.agilogy.classis.order

import org.scalatest.{FunSpec, NonImplicitAssertions}

import com.agilogy.classis.order.std.OrderStdInstances._
import Order.syntax._
import Order._

class Example(val value: Int)
case class Coords(x:Int, y:Int)

@SuppressWarnings(Array("unused"))
class OrderStdInstancesTest extends FunSpec with NonImplicitAssertions {

  private implicit val exampleOrdering: Order[Example] = Order.fromComparison[Example](_.value < _.value, _.value == _.value)

  describe("syntax") {

    it("should define operators") {
      assert(new Example(0) < new Example(1))
      assert(new Example(0) lt new Example(1))
      assert(new Example(0) <= new Example(1))
      assert(new Example(0) lteq new Example(1))
      assert(new Example(0) === new Example(0))
      assert(new Example(1) >= new Example(0))
      assert(new Example(1) gteq new Example(0))
      assert(new Example(1) > new Example(0))
      assert(new Example(1) gt new Example(0))
      assert(3.max(4) === 4)
      assert(3.min(4) === 3)
    }

  }

  it("should define an order for all numeric primitive types") {
    assert(Order[Byte].min(3, 5) === 3)
    assert(Order[Short].min(3, 5) === 3)
    assert(Order[Int].min(3, 5) === 3)
    assert(Order[Long].min(3l, 5l) === 3l)
    assert(Order[Float].min(3.0f, 5.0f) === 3.0f)
    assert(Order[Double].min(3.0, 5.0) === 3.0)
  }

  it("should NOT include an order for chars or strings, as it depends on the language collation") {
    assertTypeError("Order[String]")
    assertTypeError("Order[Char]")
    assertTypeError("\"hola\" gt \"adéu\"")
  }

  it("can be mixed with Equals") {
    import com.agilogy.classis.equal.std.EqualStdInstances._
    import com.agilogy.classis.equal.Equal.syntax._
    assert("hola" === "hola")
    assert(new Example(1) < new Example(2))
  }

  it("should derive an order for tuples"){
    assert((3,4.0) < ((4,5.0)))
    assert((3,4.0) < ((3,5.0)))
  }

  it("should derive an order for case classes"){
    assert(Coords(0,23) < Coords(1,12))
    assert(Coords(0,23) > Coords(0,12))
  }

}
