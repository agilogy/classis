package com.agilogy.classis.monoid

import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

trait MonoidStdInstancesLow {
  implicit def optionZeroInstance[T]:Zero[Option[T]] = new Zero[Option[T]] {
    override def zero: Option[T] = Option.empty[T]
  }
}

trait MonoidStdInstances extends MonoidStdInstancesLow{

  implicit def numericMonoidInstance[T:Numeric]:Monoid[T] = new Monoid[T] {

    private val numericEvidence = implicitly[Numeric[T]]

    override def zero: T = numericEvidence.zero

    override def append(x: T, y: T): T = numericEvidence.plus(x,y)
  }

//  type CanBuildEmpty[C[_]] = CanBuildFrom[C[T],T,C[T]] forSome {type T}
//  implicit def traversableMonoidInstance[C[X] <: TraversableLike[X,C[X]], T](implicit cbf:CanBuildEmpty[C]):Monoid[C[T]] = new Monoid[C[T]] {
  implicit def traversableMonoidInstance[C[X] <: TraversableLike[X,C[X]], T](implicit cbf:CanBuildFrom[C[T],T,C[T]]):Monoid[C[T]] = new Monoid[C[T]] {

    override def zero: C[T] = {
      cbf.apply().result()
    }

    override def append(x: C[T], y: C[T]): C[T] = x ++ y
  }

  implicit def mapMonoidInstance[M[X,Y] <: Map[X,Y], K, V](implicit cbf:CanBuildFrom[M[K,V],(K,V),M[K,V]]):Monoid[M[K,V]] = new Monoid[M[K,V]] {

    override def zero: M[K, V] = cbf.apply().result()

    override def append(x: M[K, V], y: M[K, V]): M[K, V] = (x ++ y).asInstanceOf[M[K,V]]
  }

  implicit def optionMonoidInstance[T:Semigroup]:Monoid[Option[T]] = new Monoid[Option[T]] {

    override def zero: Option[T] = None

    override def append(x: Option[T], y: Option[T]): Option[T] = (x,y) match {
      case (None, None) => None
      case (Some(xx), None) => Some(xx)
      case (None, Some(yy)) => Some(yy)
      case (Some(xx), Some(yy)) => Some(xx append yy)
    }
  }

}
