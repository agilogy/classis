package com.agilogy.classis.zero

import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

trait ZeroStdInstances {

  implicit def optionZeroInstance[T]:Zero[Option[T]] =
    Zero.create(Option.empty[T])

  implicit def numericZeroInstance[T: Numeric]: Zero[T] =
    Zero.create(implicitly[Numeric[T]].zero)

  implicit def traversableZeroInstance[C[X] <: TraversableLike[X, C[X]], T](implicit cbf: CanBuildFrom[C[T], T, C[T]]): Zero[C[T]] =
    Zero.create(cbf.apply().result())

  implicit def mapZeroInstance[M[X, Y] <: Map[X, Y], K, V](implicit cbf: CanBuildFrom[M[K, V], (K, V), M[K, V]]): Zero[M[K, V]] =
    Zero.create(cbf.apply().result())

  implicit val stringZeroInstance: Zero[String] =
    Zero.create("")


}

object ZeroStdInstances extends ZeroStdInstances{

}
