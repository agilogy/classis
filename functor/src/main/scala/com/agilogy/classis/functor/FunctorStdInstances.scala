package com.agilogy.classis.functor

import scala.collection.TraversableLike
import scala.language.higherKinds

trait FunctorStdInstancesLow {

  def traversableFunctorInstance[C[X] <: TraversableLike[X, C[X]]](implicit cbs: CanBuildSelf[C]): Functor[C] = new Functor[C] {
    override def map[T1, T2](fa: C[T1])(f: (T1) => T2): C[T2] = {
      implicit val cbf = cbs.builder[T1,T2]
      fa.map(f)
    }
  }

}

trait FunctorStdInstances extends FunctorStdInstancesLow{

  implicit val optionFunctorInstance = new Functor[Option] {
    override def map[T1, T2](fa: Option[T1])(f: T1 => T2): Option[T2] = fa.map(f)
  }

  implicit def eitherFunctorInstance[L] = new Functor[Either[L,?]] {
    override def map[A, B](fa: Either[L, A])(f: A => B): Either[L, B] = fa.map(f)
  }

  
  implicit lazy val listFunctorInstance: Functor[List] = traversableFunctorInstance[List]
  implicit lazy val seqFunctorInstance: Functor[Seq] = traversableFunctorInstance[Seq]
  // There is no functor for Set (see http://typelevel.org/blog/2014/06/22/mapping-sets.html)
  // implicit lazy val setFunctorInstance: Functor[Set] = traversableFunctorInstance[Set]
  implicit lazy val streamFunctorInstance: Functor[Stream] = traversableFunctorInstance[Stream]

  implicit val function0FunctorInstance:Functor[Function0] = new Functor[Function0] {
    override def map[A, B](fa: () => A)(f: A => B): () => B = () => f(fa())
  }

  implicit def function1FunctorInstance[I]:Functor[I => ?] = new Functor[I => ?] {
    override def map[A, B](fa: I => A)(f: A => B): I => B = a => f(fa(a))
  }

}

object FunctorStdInstances extends FunctorStdInstances