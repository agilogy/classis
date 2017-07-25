package com.agilogy.classis.applicative

import com.agilogy.classis.functor.Functor
import simulacrum.{noop, typeclass}

import scala.language.{higherKinds, implicitConversions}


trait ApplyFunction[F[_]]{
  def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]
}

@typeclass(excludeParents = List("ApplyFunction"))
trait Apply[F[_]] extends Functor[F] with ApplyFunction[F]{

  @noop
  def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]

  @noop
  def applyU[A,B](fab: F[A => B], fa: F[A]): F[B] = apply(fab)(fa)

}

object Apply{

  def apply[F[_]](a:ApplyFunction[F], functor:Functor[F]):Apply[F] = new Apply[F]{

    override def apply[A, B](fab: F[(A) => B])(fa: F[A]): F[B] = a.apply(fab)(fa)

    override def map[T1, T2](fa: F[T1])(f: (T1) => T2): F[T2] = functor.map(fa)(f)
  }
}
