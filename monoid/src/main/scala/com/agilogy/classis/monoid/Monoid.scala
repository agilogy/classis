package com.agilogy.classis.monoid

import simulacrum._

import scala.language.implicitConversions

@typeclass trait Monoid[T] extends Zero[T] with Semigroup[T] {

}
