package com.agilogy.classis.monoid

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.equal.EqualBasedLaws._
import com.agilogy.classis.laws.Law1

import scala.language.implicitConversions

trait Monoid[T] extends Zero[T] with Semigroup[T]

object Monoid {

  def apply[T](implicit instance: Monoid[T]): Monoid[T] = instance

  trait Syntax[T] {
    def self: T

    def typeClassInstance: Monoid[T]
  }

  trait ToMonoidSyntax {

    implicit def toMonoidSyntax[T](target: T)(implicit instance: Monoid[T]): Syntax[T] = new Syntax[T] {

      override def self: T = target

      override def typeClassInstance: Monoid[T] = instance
    }

  }

  trait ToAllMonoidSyntax extends ToMonoidSyntax with Zero.ToAllZeroSyntax with Semigroup.ToAllSemigroupSyntax

  object syntax extends ToAllMonoidSyntax

  implicit def instance[T](implicit z: Zero[T], s: Semigroup[T]): Monoid[T] = new Monoid[T] {

    override def append(x: T, y: T): T = s.append(x, y)

    override def zero: T = z.zero

  }

  trait Laws[T] extends Semigroup.Laws[T]{
    def typeClassInstance: Monoid[T]
    def leftIdentityAppendZero(implicit eq:Equal[T]): Law1[T] = leftIdentity("append", "zero", typeClassInstance.append, typeClassInstance.zero)
    def rightIdentityAppendZero(implicit eq:Equal[T]): Law1[T] = rightIdentity("append", "zero", typeClassInstance.append, typeClassInstance.zero)
  }

  def laws[T](implicit instance: Monoid[T]) = new Laws[T] {
    override def typeClassInstance: Monoid[T] = instance
  }


}
