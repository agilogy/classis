package com.agilogy.classis.monoid.std

import com.agilogy.classis.monoid.Semigroup

import scala.collection.TraversableLike
import scala.collection.generic.CanBuildFrom

import scala.language.higherKinds

trait SemigroupStdInstances {

  implicit def numericSemigroupInstance[T:Numeric]:Semigroup[T] =
    Semigroup.create[T]((x,y) => implicitly[Numeric[T]].plus(x,y))

  implicit def traversableSemigroupInstance[C[X] <: TraversableLike[X,C[X]], T](implicit cbf:CanBuildFrom[C[T],T,C[T]]):Semigroup[C[T]] =
    Semigroup.create[C[T]]((x,y) => x ++ y)

  implicit def mapSemigroupInstance[M[X,Y] <: Map[X,Y], K, V]:Semigroup[M[K,V]] =
  // TODO: Not associative?
    Semigroup.create[M[K,V]]((x,y) => (x ++ y).asInstanceOf[M[K,V]])

  implicit def optionSemigroupInstance[T](implicit s:Semigroup[T]):Semigroup[Option[T]] =
    Semigroup.create[Option[T]] { (x, y) =>
      (x, y) match {
        case (None, None) => None
        case (Some(xx), None) => Some(xx)
        case (None, Some(yy)) => Some(yy)
        case (Some(xx), Some(yy)) => Some(s.append(xx,yy))
      }
    }

  implicit val stringSemigroupInstance:Semigroup[String] =
    Semigroup.create[String](_ + _)



}

object SemigroupStdInstances extends SemigroupStdInstances
