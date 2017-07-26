package com.agilogy.classis.monoid

import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

import scala.language.implicitConversions

/**
  * See https://github.com/xuwei-k/scalaz-prototype
  * See https://github.com/scalaz/scalaz/issues/1084
  * @tparam T
  */

trait Semigroup[T] extends Any with Serializable {

  def append(x: T, y: T): T

}

object Semigroup extends ProductTypeClassCompanion[Semigroup] {

  def apply[T](implicit instance:Semigroup[T]):Semigroup[T] = instance

  trait Syntax[T]{
    def self:T
    def typeClassInstance:Semigroup[T]

    def append(y:T):T = typeClassInstance.append(self,y)
  }

  trait ToSemigroupSyntax{

    implicit def toSemigroupSyntax[T](target:T)(implicit tc:Semigroup[T]):Syntax[T] = new Syntax[T] {

      override def self: T = target

      override def typeClassInstance: Semigroup[T] = tc
    }

  }

  trait ToAllSemigroupSyntax extends ToSemigroupSyntax

  object syntax extends ToAllSemigroupSyntax

  object typeClass extends ProductTypeClass[Semigroup] {

    def emptyProduct: Semigroup[HNil] = Semigroup.create((_, _) => HNil)

    def product[F, T <: HList](mh: Semigroup[F], mt: Semigroup[T]): Semigroup[F :: T] =
      Semigroup.create((a, b) => mh.append(a.head, b.head) :: mt.append(a.tail, b.tail))

    def project[F, G](instance: => Semigroup[G], to: F => G, from: G => F): Semigroup[F] =
      Semigroup.create((a, b) => from(instance.append(to(a), to(b))))

  }

  def create[T](a:(T,T) => T):Semigroup[T] = new Semigroup[T] {
    override def append(x: T, y: T): T = a(x,y)
  }
}

