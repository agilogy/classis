package com.agilogy.classis.semigroup

import com.agilogy.classis.equal.EqualBasedLaws._
import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.Law3
import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

import scala.language.implicitConversions

/**
  * See https://github.com/xuwei-k/scalaz-prototype
  * See https://github.com/scalaz/scalaz/issues/1084
  * @tparam T
  */

trait Semigroup[T] extends Serializable {

  def append(x: T, y: T): T

}

object Semigroup extends ProductTypeClassCompanion[Semigroup] with SemigroupStdInstances {

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

  trait Laws[T]{
    def typeClassInstance:Semigroup[T]
    // TODO: Make Semigroup[T] extend Equal[T]
    // This implicit means we can't check the rules of a semigroup if it is not an equals.
    // Therefore, every semigroup must be an equal
    def associativeAppend(implicit eq:Equal[T]): Law3[T, T, T] = associative[T]("append", typeClassInstance.append)
  }

  def laws[T](implicit instance: Semigroup[T]) = new Laws[T] {
    override def typeClassInstance: Semigroup[T] = instance
  }

}

