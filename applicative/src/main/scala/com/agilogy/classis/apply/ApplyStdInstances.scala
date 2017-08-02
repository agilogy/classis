package com.agilogy.classis.apply

import com.agilogy.classis.functor.{CanBuildSelf, FunctorStdInstances}
import com.agilogy.classis.pure.PureStdInstances

import scala.collection.TraversableLike
import scala.language.higherKinds

trait ApplyStdInstancesLow extends PureStdInstances with FunctorStdInstances{

  def traversableAp[C[X] <: TraversableLike[X, C[X]]](implicit cbs: CanBuildSelf[C]): Ap[C] = new Ap[C] {

    override def apply[A, B](fab: C[A => B])(fa: C[A]): C[B] = {
      implicit val cbf = cbs.builder[A => B, B]
      fab.flatMap(f => fa.map(f))
    }
  }

}

trait ApplyStdInstances extends ApplyStdInstancesLow{
  private val optionApplyFunction = new Ap[Option] {
    override def apply[A, B](fab: Option[(A) => B])(fa: Option[A]): Option[B] = fab.flatMap(f => fa.map(f))
  }
  implicit lazy val optionApplyInstance: Apply[Option] = Apply.create[Option](optionApplyFunction, optionFunctorInstance)


  private def eitherApplyFunction[L] = new Ap[Either[L,?]] {
    override def apply[A, B](fab: Either[L,(A) => B])(fa: Either[L,A]): Either[L,B] = fab.flatMap(f => fa.map(f))
  }
  implicit def eitherApplyInstance[L]: Apply[Either[L,?]] = Apply.create(eitherApplyFunction[L], eitherFunctorInstance[L])

  implicit lazy val listApplyInstance:Apply[List] = Apply.create[List](traversableAp[List], traversableFunctorInstance[List])
  implicit lazy val seqApplyInstance:Apply[Seq] = Apply.create[Seq](traversableAp[Seq], traversableFunctorInstance[Seq])
}

object ApplyStdInstances extends ApplyStdInstances