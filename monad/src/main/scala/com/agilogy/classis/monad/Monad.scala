package com.agilogy.classis.monad

import com.agilogy.classis.applicative.Applicative
import com.agilogy.classis.applicative.Applicative.ToAllApplicativeSyntax
import com.agilogy.classis.bind.Bind
import com.agilogy.classis.bind.Bind.ToAllBindSyntax

import scala.language.{higherKinds, implicitConversions}

trait Monad[F[_]] extends Applicative[F] with Bind[F]{
  override def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(a => pure(f(a)))
}

object Monad extends MonadStdInstances{

  def apply[F[_]](implicit instance:Monad[F]): Monad[F] = instance

  implicit def instance[F[_]](implicit ap:Applicative[F], b:Bind[F]):Monad[F] = new Monad[F]{

    override def flatMap[A, B](fa: F[A])(f: (A) => F[B]): F[B] = b.flatMap(fa)(f)

    override def pure[A](a: => A): F[A] = ap.pure(a)
  }

  trait Syntax[F[_], A]{
    def self: F[A]
    def typeClassInstance: Monad[F]
  }

  trait ToMonadSyntax{
    implicit def toMonadSyntax[F[_], A](target:F[A])(implicit instance:Monad[F]):Syntax[F, A] = new Syntax[F, A]{
      override def self: F[A] = target
      override def typeClassInstance: Monad[F] = instance
    }
  }

  trait ToAllMonadSyntax extends ToMonadSyntax with ToAllApplicativeSyntax with ToAllBindSyntax

  object syntax extends ToAllMonadSyntax

  trait Laws[F[_]] extends Applicative.Laws[F] with Bind.Laws[F]{
    override def typeClassInstance: Monad[F]
  }

  def laws[F[_]](implicit instance: Monad[F]): Laws[F] = new Laws[F] {
    override def typeClassInstance: Monad[F] = instance
  }
}