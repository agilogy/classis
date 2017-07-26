package com.agilogy.classis.monoid

import scala.language.implicitConversions

trait Monoid[T] extends Zero[T] with Semigroup[T]

object Monoid{

  def apply[T](implicit instance:Monoid[T]):Monoid[T] = instance

  trait Syntax[T]{
    def self:T
    def typeClassInstance: Monoid[T]
  }

  trait ToMonoidSyntax{

    implicit def toMonoidSyntax[T](target:T)(implicit instance:Monoid[T]):Syntax[T] = new Syntax[T]{

      override def self: T = target

      override def typeClassInstance: Monoid[T] = instance
    }

  }

  trait ToAllMonoidSyntax extends ToMonoidSyntax with Zero.ToAllZeroSyntax with Semigroup.ToAllSemigroupSyntax

  object syntax extends ToAllMonoidSyntax

  implicit def instance[T](implicit z:Zero[T], s:Semigroup[T]):Monoid[T] = new Monoid[T] {

    override def append(x: T, y: T): T = s.append(x,y)

    override def zero: T = z.zero

    }

}
