package com.agilogy.classis.applicative

import scala.language.{higherKinds, implicitConversions}

trait Applicative[F[_]] extends Pure[F] with Apply[F]{

  def traverse[A,B](as: List[A])(f: A => F[B]): F[List[B]] =
    as.foldRight(pure(List[B]()))((a, fbs) => map2(f(a), fbs)(_ :: _))

  override def map[A,B](fa: F[A])(f: A => B): F[B] = map2(fa, pure(()))((a, _) => f(a))

  def map2[F1,F2,FR](fa: F[F1], fb: F[F2])(f: (F1, F2) => FR): F[FR] = {
    applyU(map(fa)(f.curried),fb)
  }

  def map3[F1, F2, F3, R](v1: F[F1],
                          v2: F[F2],
                          v3: F[F3])
                         (f: (F1, F2, F3) => R): F[R] = {
    applyU(applyU(applyU(pure(f.curried),v1),v2),v3)
  }

  def map4[F1, F2, F3, F4, R](v1: F[F1],
                              v2: F[F2],
                              v3: F[F3],
                              v4: F[F4])
                             (f: (F1, F2, F3, F4) => R): F[R] = {
    applyU(applyU(applyU(applyU(pure(f.curried),v1),v2),v3),v4)
  }

  def map5[F1, F2, F3, F4, F5, R](v1: F[F1],
                                  v2: F[F2],
                                  v3: F[F3],
                                  v4: F[F4],
                                  v5: F[F5])
                                 (f: (F1, F2, F3, F4, F5) => R): F[R] = {
    applyU(applyU(applyU(applyU(applyU(pure(f.curried),v1),v2),v3),v4),v5)
  }

  def map6[F1, F2, F3, F4, F5, F6, R](v1: F[F1],
                                      v2: F[F2],
                                      v3: F[F3],
                                      v4: F[F4],
                                      v5: F[F5],
                                      v6: F[F6])
                                     (f: (F1, F2, F3, F4, F5, F6) => R): F[R] = {
    applyU(applyU(applyU(applyU(applyU(applyU(pure(f.curried),v1),v2),v3),v4),v5),v6)
  }

}

object Applicative{

  def apply[F[_]](implicit instance: Applicative[F]):Applicative[F] = instance

  trait Syntax[F[_],A]{
    def self:F[A]
    def typeClassInstance: Applicative[F]

  }

  trait ToApplicativeSyntax{

    implicit def toApplicativeSyntax[F[_],T](target:F[T])(implicit instance:Applicative[F]):Syntax[F,T] = new Syntax[F,T] {
      override def self: F[T] = target
      override def typeClassInstance: Applicative[F] = instance
    }

    /** Needed if we don't have partial unification (https://github.com/milessabin/si2712fix-plugin)*/
    implicit def toApplicativeSyntax2[F[_,_],A,B](target:F[A,B])(implicit instance:Applicative[F[A,?]]) = new Syntax[F[A,?],B] {

      override def self: F[A, B] = target

      override def typeClassInstance: Applicative[F[A,?]] = instance
    }

  }

  trait ToAllApplicativeSyntax extends ToApplicativeSyntax with Pure.ToPureSyntax with Apply.ToApplySyntax

  object syntax extends ToAllApplicativeSyntax

}