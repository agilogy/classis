//package com.agilogy.classis.monoid
//
////import com.agilogy.classis.monoid.std.ZeroStdInstances
//import shapeless.{::, Generic, HList, HNil, Lazy, ProductTypeClass, ProductTypeClassCompanion}
//
//object ZeroDerivation extends ProductTypeClassCompanion[Zero] {
//
//  object typeClass extends ProductTypeClass[Zero] {
//
//    def emptyProduct:Zero[HNil] = Zero.create[HNil](HNil)
//
//    def product[F, T <: HList](mh: Zero[F], mt: Zero[T]): Zero[::[F, T]] = Zero.create[F :: T](mh.zero :: mt.zero)
//
//    def project[F, G](instance: => Zero[G], to: F => G, from: G => F): Zero[F] = Zero.create[F](from(instance.zero))
//  }
//
//  implicit def deriveHNilZero: Zero[HNil] = typeClass.emptyProduct
//
//  implicit def deriveHConsZero[H, T <: HList] (implicit ch: Lazy[Zero[H]], ct: Lazy[Zero[T]]): Zero[H :: T] =
//    typeClass.product(ch.value, ct.value)
//
//  implicit def deriveInstanceZero[F, G](implicit gen: Generic.Aux[F, G], cg: Lazy[Zero[G]]): Zero[F] =
//    typeClass.project(cg.value, gen.to _, gen.from _)
//
//
//}
