package com.agilogy.classis.monoid

import com.agilogy.classis.equal.Equal
import shapeless.{::, Generic, HList, HNil, Lazy, ProductTypeClass}

import scala.language.implicitConversions

import Equal.syntax._

trait Zero[T] {

  def zero:T

  def isEmpty(v:T)(implicit eq:Equal[T]):Boolean = {
    println(s"Is $v empty? true if it is $zero")
    v === zero
  }

}

object Zero extends { //ProductTypeClassCompanion[Zero]{

  def apply[T](implicit instance:Zero[T]):Zero[T] = instance

  trait Syntax[T]{
    def self:T
    def typeClassInstance: Zero[T]

    def isEmpty(implicit eq:Equal[T]):Boolean = typeClassInstance.isEmpty(self)
  }

  trait ToZeroSyntax{

    implicit def toZeroSyntax[T](target:T)(implicit instance:Zero[T]):Syntax[T] = new Syntax[T]{

      override def self: T = target

      override def typeClassInstance: Zero[T] = instance
    }

  }

  trait ToAllZeroSyntax extends ToZeroSyntax

  object syntax extends ToAllZeroSyntax

  // The typeclass object must be defined here (in the companion object to the typeclass), not imported
  // The reason is that every ProductTypeClassCompanion includes 3 implicit defs by the same name. Therefore, importing
  // more than one derivation object methods hides them all
  object typeClass extends ProductTypeClass[Zero] {

    def emptyProduct:Zero[HNil] = Zero.create[HNil](HNil)

    def product[F, T <: HList](mh: Zero[F], mt: Zero[T]): Zero[::[F, T]] = Zero.create[F :: T](mh.zero :: mt.zero)

    def project[F, G](instance: => Zero[G], to: F => G, from: G => F): Zero[F] = {
      Zero.create[F](from(instance.zero))
    }
  }

  // Inspired in ProductTypeClassCompanion, but avoids the creation of a Zero instance for classes of arity 0 (like `case object True`)
  implicit def derive1[T](implicit ct: Lazy[Zero[T]]): Zero[T :: HNil] = Zero.create[T :: HNil](ct.value.zero :: HNil)

  implicit def deriveHCons[H, T <: HList] (implicit ch: Lazy[Zero[H]], ct: Lazy[Zero[T]]): Zero[H :: T] =
    typeClass.product(ch.value, ct.value)

  implicit def deriveInstance[F, G](implicit gen: Generic.Aux[F, G], cg: Lazy[Zero[G]]): Zero[F] =
    typeClass.project[F, G](cg.value, gen.to, gen.from)

  def create[T](z:T) = new Zero[T] {
    override def zero: T = z
    if(s"$z" == "MyTrue") throw new Exception
  }


}
