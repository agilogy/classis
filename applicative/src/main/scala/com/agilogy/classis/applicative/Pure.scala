package com.agilogy.classis.applicative

import scala.language.{higherKinds, implicitConversions}

trait Pure[F[_]] {

  def pure[A](a: => A): F[A]

}

object Pure{

  def apply[F[_]](implicit instance: Pure[F]):Pure[F] = instance

  trait Syntax[F[_],A]{
    def self:F[A]
    def typeClassInstance: Pure[F]
  }

  trait ToPureSyntax{

    implicit def toPureSyntax[F[_],T](target:F[T])(implicit instance:Pure[F]):Syntax[F,T] = new Syntax[F,T]{

      override def self: F[T] = target

      override def typeClassInstance: Pure[F] = instance
    }
  }

  trait ToAllPureSyntax extends ToPureSyntax

  object syntax extends ToAllPureSyntax

}
