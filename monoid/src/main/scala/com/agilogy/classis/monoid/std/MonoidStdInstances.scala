package com.agilogy.classis.monoid.std

import com.agilogy.classis.monoid.{Monoid, Semigroup, Zero}

trait MonoidStdInstancesLow extends ZeroStdInstances with SemigroupStdInstances{

  implicit def monoidInstance[T](implicit z:Zero[T], s:Semigroup[T]):Monoid[T] = new Monoid[T] {

    override def zero: T = z.zero

    override def append(x: T, y: T): T = s.append(x,y)
  }

}

trait MonoidStdInstances extends MonoidStdInstancesLow{

//  implicit def numericMonoidInstance[T:Numeric]:Monoid[T] =
//    monoidInstance(numericZeroInstance[T],numericSemigroupInstance[T])
//
//  implicit def traversableMonoidInstance[C[X] <: TraversableLike[X,C[X]], T](implicit cbf:CanBuildFrom[C[T],T,C[T]]):Monoid[C[T]] =
//    monoidInstance(traversableZeroInstance[C,T], traversableSemigroupInstance[C,T])
//
//  implicit def mapMonoidInstance[M[X,Y] <: Map[X,Y], K, V](implicit cbf:CanBuildFrom[M[K,V],(K,V),M[K,V]]):Monoid[M[K,V]] =
//    monoidInstance(mapZeroInstance[M,K,V], mapSemigroupInstance[M,K,V])
//
//  implicit def optionMonoidInstance[T:Semigroup]:Monoid[Option[T]] =
//    monoidInstance(optionZeroInstance[T],optionSemigroupInstance[T])

}

object MonoidStdInstances extends MonoidStdInstances
