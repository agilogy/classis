package com.agilogy.classis.functor

import scala.language.{higherKinds, implicitConversions}

trait Functor[F[_]] extends Any with Serializable{

  def map[A, B](fa: F[A])(f: A => B): F[B]

}

object Functor{

  def apply[F[_]](implicit instance:Functor[F]):Functor[F] = instance

  trait Syntax[F[_],A]{
    def self:F[A]
    def typeClassInstance: Functor[F]

    def map[B](f: A => B): F[B] = typeClassInstance.map(self)(f)
    def fmap[B](f: A => B): F[B] = map(f)
  }

  trait ToFunctorSyntax{

    implicit def toFunctorSyntax[F[_],T](target:F[T])(implicit instance:Functor[F]):Syntax[F,T] = new Syntax[F,T] {
      override def self: F[T] = target
      override def typeClassInstance: Functor[F] = instance
    }

    /** Needed if we don't have partial unification (https://github.com/milessabin/si2712fix-plugin)*/
    implicit def toFunctorSyntax2[F[_,_],A,B](target:F[A,B])(implicit instance:Functor[F[A,?]]) = new Syntax[F[A,?],B] {

      override def self: F[A, B] = target

      override def typeClassInstance: Functor[F[A,?]] = instance
    }

  }

  trait ToAllFunctorSyntax extends ToFunctorSyntax

  object syntax extends ToAllFunctorSyntax

}