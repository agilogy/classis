package com.agilogy.classis.monoid

import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}
import simulacrum._

import scala.language.implicitConversions

trait ISemigroup[T] extends Any with Serializable {

  def append(x: T, y: T): T

}

/**
  * See https://github.com/xuwei-k/scalaz-prototype
  * See https://github.com/scalaz/scalaz/issues/1084
  * @tparam T
  */

@typeclass(excludeParents = List("ISemigroup"))
trait Semigroup[T] extends ISemigroup[T] {

  // Adding the override keyboard breaks Simulacrum generated stuff
  @op("append") def append(x: T, y: T): T

}

object Semigroup extends ProductTypeClassCompanion[Semigroup] {

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

