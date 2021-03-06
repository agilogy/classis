package com.agilogy.classis.pure

import com.agilogy.classis.functor.CanBuildSelf

import scala.collection.TraversableLike
import scala.language.higherKinds

trait PureStdInstancesLow{

  def traversableLikePureInstance[C[X] <: TraversableLike[X, C[X]]](implicit cbs: CanBuildSelf[C]): Pure[C] = new Pure[C] {
    override def pure[A](a: => A): C[A] = cbs.builder[A, A].apply().+=(a).result()
  }

}

trait PureStdInstances extends PureStdInstancesLow{

  implicit val optionPureInstance = new Pure[Option] {
    override def pure[A](a: => A): Option[A] = Some(a)
  }

  implicit def eitherPureInstance[L] = new Pure[Either[L,?]] {
    override def pure[A](a: => A): Either[L,A] = Right(a)
  }

  implicit lazy val listPureInstance: Pure[List] = traversableLikePureInstance[List]
  implicit lazy val seqPureInstance: Pure[Seq] = traversableLikePureInstance[Seq]
  implicit lazy val setPureInstance: Pure[Set] = traversableLikePureInstance[Set]
  implicit lazy val streamPureInstance: Pure[Stream] = traversableLikePureInstance[Stream]

}

object PureStdInstances extends PureStdInstances