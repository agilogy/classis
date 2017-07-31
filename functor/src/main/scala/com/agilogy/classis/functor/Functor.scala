package com.agilogy.classis.functor

import com.agilogy.classis.equal.{Equal, EqualBasedLaws}
import com.agilogy.classis.equal.Equal.syntax._
import com.agilogy.classis.laws.{Law1, Law3}

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

  trait Laws[F[_]]{
    def typeClassInstance: Functor[F]
    def functorIdentity[T](implicit fcEq:Equal[F[T]]): Law1[F[T]] = EqualBasedLaws.rightIdentity[F[T],T => T]("map","identity", (x, f) => typeClassInstance.map(x)(f), identity[T])
    def functorComposite[A,B,C](implicit eq:Equal[F[C]]): Law3[F[A], A => B, B => C] = Law3("composite",Seq("map","function.compose"),{
      (x:F[A], f1: A =>B, f2: B => C) =>
        typeClassInstance.map(typeClassInstance.map(x)(f1))(f2) === typeClassInstance.map(x)(x => f2(f1(x)))
    })
  }

  def laws[F[_]](implicit instance:Functor[F]):Laws[F] = new Laws[F] {
    override def typeClassInstance: Functor[F] = instance
  }

}