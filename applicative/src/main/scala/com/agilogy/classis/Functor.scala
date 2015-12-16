package com.agilogy.classis

import scala.language.{implicitConversions, higherKinds}

trait Functor[F[_]] {

  def map[T1, T2](fa: F[T1])(f: T1 => T2): F[T2]

}

object Functor {

  def apply[T[_]](implicit instance: Functor[T]): Functor[T] = instance

}

trait FunctorSyntax {

  trait Ops[F[_], T] {

    def typeClassInstance: Functor[F]

    def self: F[T]

    def map[T2](f: T => T2): F[T2] = typeClassInstance.map(self)(f)

  }

  implicit def toFunctorOps[F[_] : Functor, T](v: F[T]): Ops[F, T] = new Ops[F, T] {

    override def typeClassInstance: Functor[F] = implicitly[Functor[F]]

    override def self: F[T] = v

  }

}

object FunctorSyntax extends FunctorSyntax