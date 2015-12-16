package com.agilogy.classis

import scala.language.{implicitConversions, higherKinds}

trait Applicative[F[_]] extends Functor[F]{

  def apply[A,B](fab: F[A => B])(fa: F[A]): F[B]
  def unit[A](a: => A): F[A]

  def applyU[A,B](fab: F[A => B],fa: F[A]): F[B] = apply(fab)(fa)

  def traverse[A,B](as: List[A])(f: A => F[B]): F[List[B]] =
    as.foldRight(unit(List[B]()))((a, fbs) => map2(f(a), fbs)(_ :: _))

  def map[A,B](fa: F[A])(f: A => B): F[B] = map2(fa, unit(()))((a, _) => f(a))

  def map2[F1,F2,FR](fa: F[F1], fb: F[F2])(f: (F1, F2) => FR): F[FR] = {
    applyU(map(fa)(f.curried),fb)
  }

  def map3[F1, F2, F3, R](v1: F[F1],
                          v2: F[F2],
                          v3: F[F3])
                         (f: (F1, F2, F3) => R): F[R] = {
    applyU(applyU(applyU(unit(f.curried),v1),v2),v3)
  }

  def map4[F1, F2, F3, F4, R](v1: F[F1],
                              v2: F[F2],
                              v3: F[F3],
                              v4: F[F4])
                             (f: (F1, F2, F3, F4) => R): F[R] = {
    applyU(applyU(applyU(applyU(unit(f.curried),v1),v2),v3),v4)
  }

  def map5[F1, F2, F3, F4, F5, R](v1: F[F1],
                                  v2: F[F2],
                                  v3: F[F3],
                                  v4: F[F4],
                                  v5: F[F5])
                                 (f: (F1, F2, F3, F4, F5) => R): F[R] = {
    applyU(applyU(applyU(applyU(applyU(unit(f.curried),v1),v2),v3),v4),v5)
  }

  def map6[F1, F2, F3, F4, F5, F6, R](v1: F[F1],
                                      v2: F[F2],
                                      v3: F[F3],
                                      v4: F[F4],
                                      v5: F[F5],
                                      v6: F[F6])
                                     (f: (F1, F2, F3, F4, F5, F6) => R): F[R] = {
    applyU(applyU(applyU(applyU(applyU(applyU(unit(f.curried),v1),v2),v3),v4),v5),v6)
  }

}

object Applicative{

  def apply[T[_]](implicit instance: Applicative[T]): Applicative[T] = instance

}

trait ApplicativeSyntax{

  trait Ops[F[_], T] {

    def typeClassInstance: Applicative[F]

    def self: F[T]

  }

  trait AllOps[F[_],T] extends FunctorSyntax.Ops[F,T] with Ops[F,T]

  implicit def toApplicativeOps[F[_] : Applicative, T](v: F[T]): Ops[F, T] = new AllOps[F, T] {

    override def typeClassInstance: Applicative[F] = implicitly[Applicative[F]]

    override def self: F[T] = v

  }

}

object ApplicativeSyntax extends ApplicativeSyntax