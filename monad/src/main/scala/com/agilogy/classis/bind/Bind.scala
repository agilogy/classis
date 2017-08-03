package com.agilogy.classis.bind

import com.agilogy.classis.apply.Apply
import com.agilogy.classis.apply.Apply.ToAllApplySyntax
import com.agilogy.classis.equal.Equal
import com.agilogy.classis.equal.Equal.syntax._
import com.agilogy.classis.flatmap.FlatMap
import com.agilogy.classis.flatmap.FlatMap.ToAllFlatMapSyntax
import com.agilogy.classis.laws.{Law, Law2}

import scala.language.{higherKinds, implicitConversions}

trait Bind[F[_]] extends FlatMap[F] with Apply[F] {

  override def apply[A, B](fab: F[A => B])(fa: F[A]): F[B] = flatMap(fab)(f => map(fa)(f))

  override def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = flatMap(fa)(a => map(fb)(b => (a,b)))
}

object Bind extends BindStdInstances{

  def apply[F[_]](implicit instance: Bind[F]): Bind[F] = instance

  implicit def bindInstance[F[_]](implicit fmi: FlatMap[F], ai: Apply[F]): Bind[F] = new Bind[F] {

    override def flatMap[A, B](fa: F[A])(f: (A) => F[B]): F[B] = fmi.flatMap(fa)(f)

    override def map[A, B](fa: F[A])(f: (A) => B): F[B] = ai.map(fa)(f)
  }

  trait Syntax[F[_], A]{
    def self: F[A]
    def typeClassInstance: Bind[F]
  }

  trait ToBindSyntax{

    implicit def toBindSyntax[F[_], A](target: F[A])(implicit instance: Bind[F]): Syntax[F, A] = new Syntax[F, A]{

      override def self: F[A] = target

      override def typeClassInstance: Bind[F] = instance
    }

  }

  trait ToAllBindSyntax extends ToBindSyntax with ToAllApplySyntax with ToAllFlatMapSyntax

  object syntax extends ToAllBindSyntax

  trait Laws[F[_]] extends FlatMap.Laws[F] with Apply.Laws[F]{

    def typeClassInstance: Bind[F]

    def bindFlatMapConsistentApply[A, B](implicit fbeq: Equal[F[B]]): Law2[F[A], F[(A) => B]] = Law.law2("flatMapCnsistentApply", "flatMap", "apply") { (fa, fab) =>
      typeClassInstance.apply(fab)(fa) === typeClassInstance.flatMap(fab)(f => typeClassInstance.map(fa)(f))
    }

  }

  def laws[F[_]](implicit instance: Bind[F]):Laws[F] = new Laws[F] {
    override def typeClassInstance: Bind[F] = instance
  }

}
