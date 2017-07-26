package com.agilogy.classis.monoid

import simulacrum._

import scala.language.implicitConversions

trait IMonoid[T] extends IZero[T] with ISemigroup[T]

@typeclass(excludeParents = List("Zero","Semigroup"))
trait Monoid[T] extends Zero[T] with Semigroup[T]

object Monoid{
  implicit def instance[T](implicit z:Zero[T], s:Semigroup[T]):Monoid[T] = new Monoid[T] {

    override def append(x: T, y: T): T = s.append(x,y)

    override def zero: T = z.zero

    }

}
