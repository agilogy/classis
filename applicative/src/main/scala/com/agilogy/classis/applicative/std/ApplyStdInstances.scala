package com.agilogy.classis.applicative.std

import com.agilogy.classis.applicative.{Apply, ApplyFunction}
import com.agilogy.classis.functor.Functor
import com.agilogy.classis.functor.std.{CanBuildSelf, FunctorStdInstances}

import scala.collection.TraversableLike

import scala.language.higherKinds

trait ApplyStdInstancesLow extends PureStdInstances with FunctorStdInstances{

  def traversableApplyFunction[C[X] <: TraversableLike[X, C[X]]](implicit cbs: CanBuildSelf[C]): ApplyFunction[C] = new ApplyFunction[C] {

    override def apply[A, B](fab: C[(A) => B])(fa: C[A]): C[B] = {
      implicit val cbf = cbs.builder[A => B, B]
      fab.flatMap(f => fa.map(f))
    }
  }

  def functorApplyFunctionInstance[F[_]](implicit applyFunction: ApplyFunction[F], functor: Functor[F]): Apply[F] = Apply(applyFunction, functor)

}

trait ApplyStdInstances extends ApplyStdInstancesLow{
  private val optionApplyFunction = new ApplyFunction[Option] {
    override def apply[A, B](fab: Option[(A) => B])(fa: Option[A]): Option[B] = fab.flatMap(f => fa.map(f))
  }

  implicit lazy val optionApplyInstance: Apply[Option] = functorApplyFunctionInstance[Option](optionApplyFunction, optionFunctorInstance)

  implicit lazy val listApplyInstance:Apply[List] = functorApplyFunctionInstance[List](traversableApplyFunction[List], traversableFunctorInstance[List])
  implicit lazy val seqApplyInstance:Apply[Seq] = functorApplyFunctionInstance[Seq](traversableApplyFunction[Seq], traversableFunctorInstance[Seq])
  implicit lazy val setApplyInstance:Apply[Set] = functorApplyFunctionInstance[Set](traversableApplyFunction[Set], traversableFunctorInstance[Set])
}

object ApplyStdInstances extends ApplyStdInstances