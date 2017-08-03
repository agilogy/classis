package com.agilogy.classis.flatmap

import com.agilogy.classis.apply.Apply.ToAllApplySyntax
import com.agilogy.classis.equal.Equal
import com.agilogy.classis.equal.Equal.syntax._
import com.agilogy.classis.laws.{Law, Law3}

import scala.language.{higherKinds, implicitConversions}

trait FlatMap[F[_]] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def flatten[A](ffa:F[F[A]]):F[A] = flatMap(ffa)(identity)

  def ifM[A](fb:F[Boolean])(caseTrue: => F[A], caseFlase: => F[A]): F[A] = flatMap(fb)(if(_) caseTrue else caseFlase)

}

object FlatMap extends FlatMapStdInstances{

  def apply[F[_]](implicit instance: FlatMap[F]): FlatMap[F] = instance

  trait Syntax[F[_], A]{
    def self: F[A]
    def typeClassInstance: FlatMap[F]

    def flatMap[B](f: A => F[B]):F[B] = typeClassInstance.flatMap(self)(f)
  }

  trait SyntaxF[F[_], A]{
    def self: F[F[A]]
    def typeClassInstance: FlatMap[F]
    def flatten:F[A] = typeClassInstance.flatten(self)
  }

  trait ToFlatMapSyntax{

    implicit def toFlatMapSyntax[F[_], A](target: F[A])(implicit instance: FlatMap[F]): Syntax[F, A] = new Syntax[F, A]{

      override def self: F[A] = target

      override def typeClassInstance: FlatMap[F] = instance
    }

    implicit def toFlatMapSyntaxF[F[_], A](target: F[F[A]])(implicit instance: FlatMap[F]): SyntaxF[F, A] = new SyntaxF[F, A]{

      override def self: F[F[A]] = target

      override def typeClassInstance: FlatMap[F] = instance
    }


  }

  trait ToAllFlatMapSyntax extends ToFlatMapSyntax with ToAllApplySyntax

  object syntax extends ToAllFlatMapSyntax

  trait Laws[F[_]] {
    def typeClassInstance: FlatMap[F]

    def flatMapAssociative[A, B, C](implicit fceq: Equal[F[C]]): Law3[F[A], (A) => F[B], (B) => F[C]] = Law.law3[F[A], A => F[B], B => F[C]]("associative", "flatMap") { (fa, afb, bfc) =>
      typeClassInstance.flatMap(typeClassInstance.flatMap(fa)(afb))(bfc) === typeClassInstance.flatMap(fa)(a => typeClassInstance.flatMap(afb(a))(bfc))
    }

  }

  def laws[F[_]](implicit instance: FlatMap[F]):Laws[F] = new Laws[F] {
    override def typeClassInstance: FlatMap[F] = instance
  }

}
