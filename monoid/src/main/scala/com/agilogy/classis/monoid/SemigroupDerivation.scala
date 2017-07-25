//package com.agilogy.classis.monoid
//
////import com.agilogy.classis.monoid.std.SemigroupStdInstances
//import shapeless.{::, Generic, HList, HNil, Lazy, ProductTypeClass, ProductTypeClassCompanion}
//
//object SemigroupDerivation extends ProductTypeClassCompanion[Semigroup] {
//
//  object typeClass extends ProductTypeClass[Semigroup] {
//
//    def emptyProduct: Semigroup[HNil] = Semigroup.create((_, _) => HNil)
//
//    def product[F, T <: HList](mh: Semigroup[F], mt: Semigroup[T]): Semigroup[F :: T] =
//      Semigroup.create((a, b) => mh.append(a.head, b.head) :: mt.append(a.tail, b.tail))
//
//    def project[F, G](instance: => Semigroup[G], to: F => G, from: G => F): Semigroup[F] =
//      Semigroup.create((a, b) => from(instance.append(to(a), to(b))))
//
//  }
//
//  implicit def deriveHNilSemigroup: Semigroup[HNil] = typeClass.emptyProduct
//
//  implicit def deriveHConsSemigroup[H, T <: HList] (implicit ch: Lazy[Semigroup[H]], ct: Lazy[Semigroup[T]]): Semigroup[H :: T] =
//    typeClass.product(ch.value, ct.value)
//
//  implicit def deriveInstanceSemigroup[F, G](implicit gen: Generic.Aux[F, G], cg: Lazy[Semigroup[G]]): Semigroup[F] =
//    typeClass.project(cg.value, gen.to _, gen.from _)
//
//
//}
