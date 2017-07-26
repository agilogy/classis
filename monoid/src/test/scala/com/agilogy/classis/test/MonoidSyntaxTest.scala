package com.agilogy.classis.test

//import com.agilogy.classis.monoid.{Monoid, Semigroup, Zero}
import com.agilogy.classis.monoid.Monoid
import org.scalatest.FunSpec

sealed trait MyBool
object MyBool{
  case object MyTrue extends MyBool
  case object MyFalse extends MyBool
  val F:MyBool = MyFalse
  val T:MyBool = MyTrue
}

class MonoidSyntaxTest extends FunSpec {

  implicit val fooMonoid = new Monoid[MyBool] {

    override def zero: MyBool = MyBool.F

    override def append(x: MyBool, y: MyBool): MyBool = (x,y) match {
      case (MyBool.F,MyBool.F) => MyBool.F
      case _ => MyBool.T
    }
  }

  import Monoid.syntax._

  it("should have zero syntax"){
    assert(MyBool.F.isEmpty === true)
    assert(MyBool.T.isEmpty === false)
    assert(MyBool.F.append(MyBool.T) === MyBool.T)

  }

}
