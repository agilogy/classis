package com.agilogy.classis.applicative

import com.agilogy.classis.equal.Equal
import com.agilogy.classis.laws.{Law, Law1, Law2}
import Equal.syntax._
import com.agilogy.classis.apply.Apply
import com.agilogy.classis.apply.Apply.ToAllApplySyntax
import com.agilogy.classis.pure.Pure
import com.agilogy.classis.pure.Pure.ToAllPureSyntax

import scala.language.{higherKinds, implicitConversions}

trait Applicative[F[_]] extends Pure[F] with Apply[F]{

  def traverse[A,B](as: List[A])(f: A => F[B]): F[List[B]] =
    as.foldRight(pure(List[B]()))((a, fbs) => map2(f(a), fbs)(_ :: _))

  override def map[A, B](fa: F[A])(f: A => B): F[B] = applyU(pure(f), fa) //map2(fa, pure(()))((a, _) => f(a))

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

object Applicative extends ApplicativeStdInstances{

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

  trait ToAllApplicativeSyntax extends ToApplicativeSyntax with ToAllPureSyntax with ToAllApplySyntax

  object syntax extends ToAllApplicativeSyntax

  trait Laws[F[_]] extends Apply.Laws[F] {
    override def typeClassInstance: Applicative[F]

    def applicativePureIdIdentity[A](implicit eq: Equal[F[A]]): Law1[F[A]] = Law.law1[F[A]]("identity", "apply", "pure", "identity") { fa =>
      typeClassInstance.apply(typeClassInstance.pure(identity[A] _))(fa) === fa
    }

    def applicativeHomomorphism[A, B](implicit eq: Equal[F[B]]): Law2[A, (A) => B] = Law.law2[A, A => B]("homomorphism", "apply", "pure") { (a, ab) =>
      typeClassInstance.apply(typeClassInstance.pure(ab))(typeClassInstance.pure(a)) === typeClassInstance.pure(ab(a))
    }

    def applicativePureIdentity[A, B](implicit eq: Equal[F[B]]): Law2[F[(A) => B], A] = Law.law2("identity", "apply", "pure") { (fab, a) =>
      typeClassInstance.apply(fab)(typeClassInstance.pure(a)) === typeClassInstance.apply(typeClassInstance.pure((f: A => B) => f(a)))(fab)
    }

    // This is only needed because an instance may overwrite the default map definition
    // We could allow this (allow defining Applicative in terms of pure and map2 instead of pure and applicative)
    // or forbid it by making map final (and then removing this law)
    def applicativeMap[A, B](implicit eq: Equal[F[B]]): Law2[F[A], A => B] = Law.law2("applicativeMap") { (fa, f) =>
      typeClassInstance.map(fa)(f) === typeClassInstance.apply(typeClassInstance.pure(f))(fa)
    }

  }

  def laws[F[_]](implicit instance: Applicative[F]): Laws[F] = new Laws[F] {
    override def typeClassInstance: Applicative[F] = instance
  }

}