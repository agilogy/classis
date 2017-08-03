package com.agilogy.classis.apply

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.equal.Equal.syntax._
import com.agilogy.classis.functor.Functor
import com.agilogy.classis.laws.Law3

import scala.language.{higherKinds, implicitConversions}

trait Ap[F[_]]{
  def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]
  def applyU[A,B](fab: F[A => B], fa: F[A]): F[B] = apply(fab)(fa)
}

trait Apply[F[_]] extends Ap[F] with Functor[F]{
  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = apply(map(fa)(a => (b: B) => (a, b)))(fb)
}

object Apply extends ApplyStdInstances{

  def apply[F[_]](implicit instance: Apply[F]): Apply[F] = instance

  trait Syntax[F[_],A]{
    def self: F[A]
    def typeClassInstance: Apply[F]

    def applyProduct[B](fb:F[B]):F[(A,B)] = typeClassInstance.product(self,fb)
  }

  trait ToApplySyntax{

    implicit def toApplySyntax[F[_],A](target:F[A])(implicit instance: Apply[F]) = new Syntax[F,A] {

      override def self: F[A] = target

      override def typeClassInstance: Apply[F] = instance
    }
  }

  trait ToAllApplySyntax extends ToApplySyntax with Functor.ToAllFunctorSyntax

  object syntax extends ToAllApplySyntax

  def create[F[_]](a:Ap[F], functor:Functor[F]):Apply[F] = new Apply[F]{

    override def apply[A, B](fab: F[(A) => B])(fa: F[A]): F[B] = a.apply(fab)(fa)

    override def map[T1, T2](fa: F[T1])(f: (T1) => T2): F[T2] = functor.map(fa)(f)
  }

  trait Laws[F[_]] extends Functor.Laws[F] {
    def typeClassInstance: Apply[F]

    def applyComposite[A,B,C](implicit eq: Equal[F[C]]): Law3[F[A], F[(A) => B], F[(B) => C]] =
      Law3("composite", Seq("ap", "map"), { (fa, fab, fbc) =>
          val v1: F[C] = typeClassInstance.apply(fbc)(typeClassInstance.apply(fab)(fa))
          val v2: F[C] = typeClassInstance.apply(typeClassInstance.apply(typeClassInstance.map(fbc)((bc: B => C) => (ab: A => B) => bc compose ab))(fab))(fa)
          v1 === v2
      })
  }

  def laws[F[_]](implicit instance:Apply[F]):Laws[F] = new Laws[F] {
    override def typeClassInstance: Apply[F] = instance
  }
}
