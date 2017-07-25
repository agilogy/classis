package com.agilogy.classis.test

import org.scalatest.FunSpec
import simulacrum.{op, typeclass}

import scala.language.implicitConversions
trait IZero2[T]{
  def zero:T
}

@typeclass(excludeParents = List("IZero2"))
trait Zero2[T] extends IZero2[T]

object Zero2{
  implicit val intInstance: Zero2[Int] = new Zero2[Int]{
    override def zero: Int = 0
  }
}

trait ISemigroup2[T]{
  def append(x: T, y: T): T
}

@typeclass(excludeParents = List("ISemigroup2"))
trait Semigroup2[T] extends ISemigroup2[T]{
  @op("append") override def append(x: T, y: T): T
}

object Semigroup2{
  implicit val intInstance:Semigroup2[Int] = new Semigroup2[Int] {
    override def append(x: Int, y: Int): Int = x + y
  }
}

trait IMonoid2[T] extends ISemigroup2[T] with IZero2[T]

@typeclass(excludeParents = List("IMonoid2"))
trait Monoid2[T] extends IMonoid2[T]

object Monoid2{

  implicit def monoid2Instance[T](implicit s:Semigroup2[T], z:Zero2[T]):Monoid2[T] = new Monoid2[T] {

    override def append(x: T, y: T): T = s.append(x,y)

    override def zero: T = z.zero
  }
}

class POC extends FunSpec{

  it("should have a zero"){
    assert(Zero2[Int].zero == 0)
    assert(Semigroup2[Int].append(3,4) == 7)
    assert(Monoid2[Int].zero == 0)
    assert(Monoid2[Int].append(3,4) == 7)
  }

}
