package com.agilogy.classis.monoid

import shapeless.{::, HList, HNil, ProductTypeClass, ProductTypeClassCompanion}

trait IZero[T]{
  def zero:T
}

trait Zero[T] extends IZero[T]{

  def zero:T

}

object Zero extends ProductTypeClassCompanion[Zero]{

  def apply[T](implicit instance:Zero[T]):Zero[T] = instance

  // The typeclass object must be defined here (in the companion object to the typeclass), not imported
  // The reason is that every ProductTypeClassCompanion includes 3 implicit defs by the same name. Therefore, importing
  // more than one derivation object methods hides them all
  object typeClass extends ProductTypeClass[Zero] {

    def emptyProduct:Zero[HNil] = Zero.create[HNil](HNil)

    def product[F, T <: HList](mh: Zero[F], mt: Zero[T]): Zero[::[F, T]] = Zero.create[F :: T](mh.zero :: mt.zero)

    def project[F, G](instance: => Zero[G], to: F => G, from: G => F): Zero[F] = Zero.create[F](from(instance.zero))
  }

  def create[T](z:T) = new Zero[T] {
    override def zero: T = z
  }


}
