package com.agilogy.classis.equal

import com.agilogy.classis.laws._
import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

import scala.language.implicitConversions

trait Equal[T] {

  def equal(x: T, y: T): Boolean

  def notEqual(x: T, y: T): Boolean = !equal(x, y)

}

object Equal extends ProductTypeClassCompanion[Equal] with EqualStdInstances{

  def apply[T](implicit instance: Equal[T]): Equal[T] = instance

  trait Syntax[T] {
    def self: T

    def typeClassInstance: Equal[T]

    def ===(y: T): Boolean = typeClassInstance.equal(self, y)

    def !==(y: T): Boolean = typeClassInstance.notEqual(self, y)
  }

  trait ToEqSyntax {

    implicit def toEqSyntax[T](target: T)(implicit instance: Equal[T]): Syntax[T] = new Syntax[T] {

      override def self: T = target

      override def typeClassInstance: Equal[T] = instance
    }
  }

  trait ToAllEqSyntax extends ToEqSyntax

  object syntax extends ToAllEqSyntax

  object typeClass extends ProductTypeClass[Equal] {

    def emptyProduct: Equal[HNil] = Equal.fromJavaEquals[HNil]

    def product[F, T <: HList](mh: Equal[F], mt: Equal[T]): Equal[::[F, T]] = Equal.create[F :: T] {
      case ((h1 :: t1), (h2 :: t2)) => mh.equal(h1, h2) && mt.equal(t1, t2)
    }

    def project[F, G](instance: => Equal[G], to: F => G, from: G => F): Equal[F] = {
      Equal.create[F]((x, y) => instance.equal(to(x), to(y)))
    }
  }

  def create[T](eqF: (T, T) => Boolean): Equal[T] = new Equal[T] {
    override def equal(x: T, y: T): Boolean = eqF(x, y)
  }

  def fromJavaEquals[T]: Equal[T] = create(_ == _)

  trait Laws[T]{
    def typeClassInstance:Equal[T]
    val reflexiveEqual: Law1[T] = Laws.reflexive("equal", typeClassInstance.equal)
    val commutativeEqual: Law2[T, T] = EqualBasedLaws.commutative("equal", typeClassInstance.equal)
    val transitiveEqual: Law3[T, T, T] = Laws.transitive("equal", typeClassInstance.equal)
  }

  def laws[T](implicit instance: Equal[T]) = new Laws[T] {
    override def typeClassInstance: Equal[T] = instance
  }

}
