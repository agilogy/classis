package com.agilogy.classis.functor

import simulacrum.{op, typeclass}

import scala.language.{higherKinds, implicitConversions}

@typeclass trait Functor[F[_]] {

  @op("fmap", alias = true) def map[A, B](fa: F[A])(f: A => B): F[B]

}

object Functor{

  trait FunctorABOps[M[_,_],A,B] extends Functor.Ops[M[A, ?],B]

  implicit def toFunctorAbOps[M[_,_],A,B](s:M[A,B])(implicit f:Functor[M[A, ?]]) = new FunctorABOps[M,A,B] {

    override val typeClassInstance: Functor[M[A, ?]] = f

    override val self: M[A, B] = s
  }

}