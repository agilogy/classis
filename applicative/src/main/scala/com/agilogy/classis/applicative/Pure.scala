package com.agilogy.classis.applicative

import simulacrum.typeclass

import scala.language.{higherKinds,implicitConversions}

@typeclass trait Pure[F[_]] {

  def pure[A](a: => A): F[A]

}
